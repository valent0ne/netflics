package it.univaq.disim.netflics.vault.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import it.univaq.disim.netflics.clients.auth.AuthPT;
import it.univaq.disim.netflics.clients.auth.CheckTokenRequest;
import it.univaq.disim.netflics.clients.auth.CheckTokenResponse;
import it.univaq.disim.netflics.vault.*;
import it.univaq.disim.netflics.vault.model.Movie;
import it.univaq.disim.netflics.vault.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import it.univaq.disim.netflics.clients.auth.AuthService;

import com.google.gson.Gson;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

@Service
public class VaultServiceImpl implements VaultService {

    @Autowired
    private MovieRepository movieRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(VaultServiceImpl.class);

    @Value("#{cfg.omdburl}")
    private String omdburl;

    @Value("#{cfg.videopath}")
    private String videopath;

    @Override
    public GetMovieResponse getMovie(GetMovieRequest parameters) throws BusinessException {

        // check credentials
        if (!auth(parameters.getToken())) {
            GetMovieResponse getMovieResponse = new GetMovieResponse();
            getMovieResponse.setMovie(null);
            getMovieResponse.setResult("unauthorized");
            return getMovieResponse;
        }

        GetMovieResponse response = new GetMovieResponse();


        FileDataSource dataSource = new FileDataSource(videopath + parameters.getImdbId());

        if (!(dataSource.getFile().exists() && dataSource.getFile().length() > 0 && dataSource.getFile().canRead())) {
            response.setMovie(null);
            response.setResult("ko/movie "+parameters.getImdbId()+" not found");

        }else{
            response.setMovie(new DataHandler(dataSource));
            response.setResult("ok");
        }

        return response;
    }

    @Override
    public AddMovieResponse addMovie(AddMovieRequest parameters) throws BusinessException {

        AddMovieResponse addMovieResponse = new AddMovieResponse();

        // check credentials
        if (!auth(parameters.getToken())) {
            addMovieResponse.setResult("unauthorized");
            return addMovieResponse;
        }

        String title = null;
        String directors = null;
        String genres = null;
        Double rating = null;

        // save file data in /resources
        DataHandler movieData = parameters.getMovie();
        String imdbId = parameters.getImdbId();

        saveVideo(movieData, new File(videopath + imdbId));
        LOGGER.info("movie saved.");

        // omdb request (for movie metadata)
        try {

            LOGGER.info("REST request to OMDB.");

            // build the url

            String url = omdburl + imdbId;
            // connect to url
            HttpURLConnection c = null;

            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    if (c != null) {
                        c.disconnect();
                    }

                    JsonObject jobj = new Gson().fromJson(sb.toString(), JsonObject.class);
                    title = jobj.get("Title").getAsString();
                    directors = jobj.get("Director").getAsString();
                    genres = jobj.get("Genre").getAsString();
                    rating = jobj.get("imdbRating").getAsDouble();

                    LOGGER.info("movie data retrieved: {} {} {} {}", title, directors, genres, rating);

                    break;

                default:
                    if (c != null) {
                        c.disconnect();
                    }
                    throw new Exception("Http Error: " + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            addMovieResponse.setResult("ko/omdb rest call failed");
            return addMovieResponse;
        }

        // movie object instantiation
        Movie m = new Movie();
        m.setTitle(title);
        m.setDirectors(directors);
        m.setGenres(genres);
        m.setRating(rating);
        m.setImdbId(imdbId);

        // insertion to the db
        movieRepository.save(m);

        // return response
        addMovieResponse.setResult("ok");

        LOGGER.info("movie added successfully");

        return addMovieResponse;
    }

    /**
     * utility function that is used to save
     *
     * @param dataHandler container that contains the data
     * @param filePath    where to save the file
     */
    private static void saveVideo(final DataHandler dataHandler, final File filePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            dataHandler.writeTo(fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
    }

    /**
     * calls the auth service to check the user's credentials (token)
     *
     * @param token user's token
     * @return true if the token is valid and the user is an ADMIN
     */
    private boolean auth(String token) {
        AuthService authService = new AuthService();
        AuthPT authPT = authService.getAuthPort();
        CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
        checkTokenRequest.setToken(token);
        CheckTokenResponse checkTokenResponse = authPT.checkToken(checkTokenRequest);

        return (checkTokenResponse.isResult() && checkTokenResponse.getRole().equals("ADMIN"));
    }

}
