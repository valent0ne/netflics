package it.univaq.disim.netflics.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static int numrequest=10;
    private static int waitbetweenrequests=2;
    private static String imdbid = "tt5013056";
    private static String targetaddress = "localhost:8080";

    public static void main(String[] args){

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("cfg.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find cfg.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value
            numrequest = Integer.parseInt(prop.getProperty("numrequest"));
            waitbetweenrequests = Integer.parseInt(prop.getProperty("waitbetweenrequests"));
            imdbid = prop.getProperty("imdbid");
            targetaddress = prop.getProperty("targetaddress");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        LOGGER.info("number of requests to perform: {}, each one every {} seconds", numrequest, waitbetweenrequests);
        LOGGER.info("starting now...");

        List<Integer> requests = IntStream.rangeClosed(0, numrequest).boxed().collect(Collectors.toList());

        try {
            ExecutorService testThreadPool = Executors.newFixedThreadPool(numrequest);
            for (Integer i : requests) {
                // LOGGER.info("sending request {}/{}...", i, numrequest);
                testThreadPool.submit(() -> sendRequest(targetaddress, imdbid, i));
                TimeUnit.SECONDS.sleep(waitbetweenrequests);
            }
            testThreadPool.shutdown();
            testThreadPool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);

        } catch (Exception e) {
            LOGGER.warn("error sending requests: {}", e.getMessage());
        }

    }

    private static void sendRequest(String targetaddress, String imdbid, int i){
        Client client = ClientBuilder.newClient();
        LOGGER.info("request {} starting now...", i);
        WebTarget target = client.target("http://"+targetaddress+"/dispatcher/api/dispatcher/"+i+"/movie/stream/"+imdbid);
        InputStream is = target.request(MediaType.APPLICATION_OCTET_STREAM).get(InputStream.class);
        LOGGER.info("request {} ended", i);

    }



}
