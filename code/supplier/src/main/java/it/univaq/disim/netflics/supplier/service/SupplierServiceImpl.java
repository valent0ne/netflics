package it.univaq.disim.netflics.supplier.service;

import com.sun.management.OperatingSystemMXBean;
import it.univaq.disim.netflics.clients.vault.GetMovieRequest;
import it.univaq.disim.netflics.clients.vault.GetMovieResponse;
import it.univaq.disim.netflics.clients.vault.VaultPT;
import it.univaq.disim.netflics.clients.vault.VaultService;
import it.univaq.disim.netflics.supplier.model.Availability;
import it.univaq.disim.netflics.supplier.model.SupplierMovie;
import it.univaq.disim.netflics.supplier.repository.AvailabilityRepository;
import it.univaq.disim.netflics.supplier.repository.MovieRepository;
import it.univaq.disim.netflics.supplier.repository.SupplierMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import it.univaq.disim.netflics.supplier.BusinessException;

import javax.activation.DataHandler;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;


@Service
public class SupplierServiceImpl implements SupplierService {


    private static Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Value("#{cfg.videopath}")
    private String videopath;

    @Value("#{cfg.supplier_id}")
    private Long supplierId;

    @Value("#{cfg.token}")
    private String token;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private SupplierMovieRepository supplierMovieRepository;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * retrieves the movie from the filesystem and returns it
     *
     * @param imdbId the movie identifier
     * @return the movie file
     */
    public File getMovie(String imdbId) {

        String pathToFile = videopath + imdbId;
        File file = new File(pathToFile);
        if (!file.exists()) {
            LOGGER.error("the movie is not available on this supplier");

            // remove entry from db to signal that this supplier doesn't have the requested movie
            SupplierMovie sm = new SupplierMovie();
            sm.setSupplierId(supplierId);
            sm.setMovieId(movieRepository.findOneByImdbId(imdbId).getId());
            supplierMovieRepository.delete(sm);

            return null;
        }
        return file;
    }

    /**
     * calculates the system's % of occupied cpu and memory
     *
     * @return the aforementioned values
     */
    public Availability getAvailability() {

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        Availability availability = new Availability();

        Timestamp ts = new Timestamp(System.currentTimeMillis());

        double occupiedCpuPercentage = osBean.getSystemCpuLoad();
        double occupiedMemPercentage = ((double) osBean.getFreePhysicalMemorySize() / osBean.getTotalPhysicalMemorySize()) * 100;

        // it could happen that the reads are incorrect from time to time
        // it usually takes a bit of time to get cpu readings
        if (occupiedCpuPercentage != 0 && occupiedCpuPercentage != 0) {
            availability.setSupplier_id(supplierId);
            availability.setAvailable(true);
            availability.setCpuSaturation(occupiedCpuPercentage);
            availability.setMemSaturation(occupiedMemPercentage);
            availability.setTimestamp(ts);

            availabilityRepository.save(availability);
        } else {

            availability = null;
        }

        return availability;
    }


    /**
     * retrieves a missing movie from the Vault service
     *
     * @param imdbId them ovie identifier
     */
    public void fetchMovie(String imdbId) {
        VaultService vaultService = new VaultService();
        VaultPT vaultPT = vaultService.getVaultPort();
        GetMovieRequest getMovieRequest = new GetMovieRequest();
        getMovieRequest.setToken(token);
        getMovieRequest.setImdbId(imdbId);
        GetMovieResponse getMovieResponse = vaultPT.getMovie(getMovieRequest);
        DataHandler dh = getMovieResponse.getMovie();
        String result = getMovieResponse.getResult();

        // if the vault service returned an error
        if (result.equals("ok")) {
            // save movie to disk
            try {
                File file = new File(videopath + imdbId);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                dh.writeTo(fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                LOGGER.info("movie retrieved");
            } catch (IOException e) {
                LOGGER.error("can't save video file to disk");
                throw new BusinessException(e);
            }

            // add entry into db to signal that this supplier now has the requested movie
            SupplierMovie sm = new SupplierMovie();
            sm.setSupplierId(supplierId);
            sm.setMovieId(movieRepository.findOneByImdbId(imdbId).getId());
            supplierMovieRepository.save(sm);
            LOGGER.info("supplier-movie db data updated");

        } else {
            throw new BusinessException("couldn't fetch movie from vault service");
        }

    }


}
