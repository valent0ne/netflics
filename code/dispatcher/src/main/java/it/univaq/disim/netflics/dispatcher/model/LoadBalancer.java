package it.univaq.disim.netflics.dispatcher.model;

import it.univaq.disim.netflics.dispatcher.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoadBalancer {

    @Autowired
    private SupplierRepository supplierRepository;

    @Value("#{cfg.freeslotsthreshold}")
    private int freeSlotsThreshold;

    @Value("#{cfg.numsupplierstofetch}")
    private int numSuppliersToFetch;

    private HashMap<Supplier,Availability> freeSuppliers;
    private HashMap<Supplier,Availability> freeSuppliersHavingMovie;
    private HashMap<Supplier,Availability> freeSuppliersNotHavingMovie;

    private List<Supplier> suppliersToWakeUp;
    private List<Supplier> suppliersToSleep;
    private List<Supplier> suppliersToFatch;

    private String token;
    private String imdbId;


    public LoadBalancer(String token, String imdbId){
        this.freeSuppliers = new HashMap<>();
        this.freeSuppliersHavingMovie = new HashMap<>();
        this.freeSuppliersNotHavingMovie = new HashMap<>();
        this.suppliersToWakeUp = new ArrayList<>();
        this.suppliersToSleep = new ArrayList<>();
        this.suppliersToFatch = new ArrayList<>();
        this.token = token;
        this.imdbId = imdbId;
    }

    public void monitor(){

        List<Supplier> suppliersToPoll;
        suppliersToPoll = supplierRepository.findAll();
        if(suppliersToPoll !=null && suppliersToPoll.size()>0) {
            // call getAvailability of all suppliers
            try {
                ExecutorService getAvailabilityThreadPool = Executors.newFixedThreadPool(suppliersToPoll.size());
                for (Supplier s : suppliersToPoll) {
                    getAvailabilityThreadPool.submit(() -> sendGetAvailability(s, token));
                }
                getAvailabilityThreadPool.shutdown();
                getAvailabilityThreadPool.awaitTermination(3000, TimeUnit.MILLISECONDS);

            } catch (Exception e) {
                LOGGER.warn("500/error while polling the suppliers, continuing anyway: {}", e.getMessage());
                // throw new BusinessException("500/error while polling the suppliers");
                // try to continue anyway...the suppliers will be polled again during the next method invocation
            }

            // TODO

        }
    }


    /**
     * sends a getAvailability request to the supplier, if the response is valid,
     * the availability data is stored in a map
     * @param s supplier object
     */
    private void sendGetAvailability(Supplier s, String token){
        LOGGER.info("sending getAvailability to supplier: {} - {}:{}, token: {}", s.getId(), s.getIp(), s.getPort(), token);

        Availability a;
        supplierAvailability.remove(s);

        try{
            Client client = ClientBuilder.newClient();
            WebTarget target = client
                    .target("http://"+s.getIp()+":"+s.getPort()+"/supplier/api/supplier/"+token+"/availability");
            a = target.request(MediaType.APPLICATION_JSON).get(Availability.class);
        }catch (Exception e){
            //e.printStackTrace();
            LOGGER.error("the supplier "+s.getId()+" seems unavailable: {}", e.getMessage());
            availabilityRepository.setUnavailable(s);
            return;
        }

        if(a != null && a.getAvailable()){
            LOGGER.info("supplier "+a.getSupplier_id()+" is available");
            supplierAvailability.put(s, a);
        }
    }

}
