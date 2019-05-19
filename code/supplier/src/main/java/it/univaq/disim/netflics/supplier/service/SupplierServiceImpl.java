package it.univaq.disim.netflics.supplier.service;

import com.sun.management.OperatingSystemMXBean;
import it.univaq.disim.netflics.clients.auth.AuthPT;
import it.univaq.disim.netflics.clients.auth.AuthService;
import it.univaq.disim.netflics.clients.auth.CheckTokenRequest;
import it.univaq.disim.netflics.clients.auth.CheckTokenResponse;
import it.univaq.disim.netflics.clients.vault.GetMovieRequest;
import it.univaq.disim.netflics.clients.vault.GetMovieResponse;
import it.univaq.disim.netflics.clients.vault.VaultPT;
import it.univaq.disim.netflics.clients.vault.VaultService;
import it.univaq.disim.netflics.supplier.model.Availability;
import it.univaq.disim.netflics.supplier.model.Movie;
import it.univaq.disim.netflics.supplier.model.SupplierMovie;
import it.univaq.disim.netflics.supplier.repository.AvailabilityRepository;
import it.univaq.disim.netflics.supplier.repository.MovieRepository;
import it.univaq.disim.netflics.supplier.repository.SupplierMovieRepository;
import it.univaq.disim.netflics.supplier.repository.SupplierRepository;
import org.apache.commons.io.FileUtils;
import org.apache.cxf.Bus;
import org.apache.cxf.helpers.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import it.univaq.disim.netflics.supplier.BusinessException;

import javax.activation.DataHandler;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Value("#{cfg.videopath}")
    private String videopath;

    @Value("#{cfg.supplier_id}")
    private Long supplierId;

    @Value("#{cfg.token}")
    private String token;

    @Value("#{cfg.max_active_clients}")
    private int maxActiveClients;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private SupplierMovieRepository supplierMovieRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private static AtomicInteger activeClients = new AtomicInteger(0);

    /**
     * retrieves the movie from the filesystem and returns it
     *
     * @param imdbId the movie identifier
     * @return the movie file
     */
    public StreamingOutput getMovie(String token, String imdbId) {

        // check credentials
        if (!auth(token)) {
            throw new BusinessException("401/token not valid");
        }

        String pathToFile = videopath + imdbId;
        File file = new File(pathToFile);

        if (!file.exists()) {
            LOGGER.error("the movie is not available on this supplier, token: {}", token);

            // remove entry from db to signal that this supplier doesn't have the requested
            // movie
            SupplierMovie sm = new SupplierMovie();
            sm.setSupplierId(supplierId);
            Movie m = movieRepository.findOneByImdbId(imdbId);
            if (m == null) {
                return null;
            }
            sm.setMovieId(m.getId());
            // if there is a wrong entry into the db, clean it
            if (sm.getMovieId() != null) {
                supplierMovieRepository.delete(sm);
            }
            return null;
        }
        LOGGER.info("movie " + imdbId + " has been found, token: {}", token);

        return outputStream -> {
            try {
                int currentActiveClients = activeClients.incrementAndGet();
                LOGGER.info("streaming...free slots: {}, token: {}", maxActiveClients - currentActiveClients, token);
                // artificial delay
                // TimeUnit.SECONDS.sleep(10);

                InputStream is = FileUtils.openInputStream(file);
                IOUtils.copy(is, outputStream);
                // outputStream.write(is.read());
                outputStream.flush();

            } catch (Exception ignored) {

            } finally {
                int currentActiveClients = activeClients.decrementAndGet();
                LOGGER.info("stream ended, free slots: {}, token: {}", maxActiveClients - currentActiveClients, token);
                outputStream.close();

            }
        };
    }

    /**
     * awakes this supplier
     *
     * @param token auth token
     */
    public void wakeUp(String token) {
        // check credentials
        if (!auth(token)) {
            throw new BusinessException("401/token not valid");
        }
        supplierRepository.setAwake(supplierId);
        LOGGER.info("this supplier has been woken up, token: {}", token);
    }

    /**
     * put this supplier to sleep
     *
     * @param token auth token
     */
    public void sleep(String token) {
        // check credentials
        if (!auth(token)) {
            throw new BusinessException("401/token not valid");
        }
        if (supplierMovieRepository.findAllByStatusFetching(supplierId).size() > 0) {
            LOGGER.warn("cant go to sleep, I'm retrieving movies, token: {}", token);
            return;
        }

        supplierRepository.setSleep(supplierId);
        supplierMovieRepository.deleteAllBySupplierId(supplierId);
        try {
            FileUtils.cleanDirectory(new File(videopath));
        } catch (IOException e) {
            // e.printStackTrace();
            LOGGER.warn("could not delete video files");
        }
        LOGGER.info("this supplier has been put to sleep, token: {}", token);
    }

    /**
     * calculates the system's % of occupied cpu and memory
     *
     * @return the aforementioned values
     */
    public Availability getAvailability(String token) {

        // check credentials
        if (!auth(token)) {
            throw new BusinessException("401/token not valid");
        }

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        Availability availability = null;

        Timestamp ts = new Timestamp(System.currentTimeMillis());

        // Double occupiedCpuPercentage = osBean.getSystemCpuLoad();
        // Double occupiedMemPercentage = (((double) osBean.getTotalPhysicalMemorySize()
        // - (double)osBean.getFreePhysicalMemorySize()) /
        // (double)osBean.getTotalPhysicalMemorySize());

        // DecimalFormat df = new DecimalFormat("#.##");
        // df.setRoundingMode(RoundingMode.UP);

        // occupiedCpuPercentage = new Double(df.format(occupiedCpuPercentage));
        // occupiedMemPercentage = new Double(df.format(occupiedMemPercentage));

        // occupiedCpuPercentage = new
        // BigDecimal(occupiedCpuPercentage.toString()).setScale(2,
        // RoundingMode.HALF_UP).doubleValue();
        // occupiedMemPercentage = new
        // BigDecimal(occupiedMemPercentage.toString()).setScale(2,
        // RoundingMode.HALF_UP).doubleValue();

        Integer freeSlots = maxActiveClients - activeClients.get();

        // it could happen that the reads are incorrect from time to time
        // it usually takes a bit of time to get cpu readings
        if (freeSlots >= 0) {
            availability = new Availability();
            availability.setSupplier_id(supplierId);
            availability.setAvailable(true);
            availability.setCpuSaturation(0.0);
            availability.setMemSaturation(0.0);
            availability.setTimestamp(ts);
            availability.setFreeSlots(freeSlots);

            availabilityRepository.save(availability);
        }

        return availability;
    }

    /**
     * retrieves a missing movie from the Vault service
     *
     * @param imdbId them ovie identifier
     */
    public void fetchMovie(String token, String imdbId) throws BusinessException {

        // check credentials
        if (!auth(token)) {
            throw new BusinessException("401/token not valid");
        }

        SupplierMovie sm = new SupplierMovie();
        sm.setSupplierId(supplierId);
        Movie m = movieRepository.findOneByImdbId(imdbId);
        if (m == null) {
            throw new BusinessException("404/the requested movie cannot be found");
        }
        sm.setMovieId(m.getId());
        sm.setStatus("FETCHING");

        File file = new File(videopath + imdbId);

        if (file.exists() && file.length() > 0) {
            throw new BusinessException("500/movie already available");
        }

        // signal that the supplier is fetching the movie
        supplierMovieRepository.delete(sm);
        supplierMovieRepository.save(sm);

        VaultService vaultService = new VaultService();
        VaultPT vaultPT = vaultService.getVaultPort();
        GetMovieRequest getMovieRequest = new GetMovieRequest();
        getMovieRequest.setToken(token);
        getMovieRequest.setImdbId(imdbId);
        GetMovieResponse getMovieResponse = vaultPT.getMovie(getMovieRequest);
        DataHandler dh = getMovieResponse.getMovie();
        String result = getMovieResponse.getResult();
        String status = result.substring(0, 3);

        // if the vault service did not return an error
        if (status.equals("200")) {
            // save movie to disk
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                dh.writeTo(fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                LOGGER.info("movie retrieved, token: {}", token);
            } catch (IOException e) {
                // cleanup db
                supplierMovieRepository.delete(sm);
                throw new BusinessException("500/can't save video file to disk");
            }

            // add entry into db to signal that this supplier now has the requested movie
            sm.setStatus("FETCHED");
            supplierMovieRepository.update(sm);
            LOGGER.info("supplier-movie db data updated, token: {}", token);

        } else {
            // clean up the db
            supplierMovieRepository.delete(sm);
            throw new BusinessException(result);
        }

    }

    /**
     * calls the auth service to check the user's credentials (token)
     *
     * @param token user's token
     * @return true if the token is valid
     */
    private boolean auth(String token) {

        AuthService authService = new AuthService();
        AuthPT authPT = authService.getAuthPort();
        CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
        checkTokenRequest.setToken(token);
        CheckTokenResponse checkTokenResponse = authPT.checkToken(checkTokenRequest);

        return (checkTokenResponse.isResult());
    }

}
