package it.univaq.disim.netflics.vault.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.google.gson.*;
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

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

@Service
@SuppressWarnings("Duplicates")
public class VaultServiceImpl implements VaultService {

    @Autowired
    private MovieRepository movieRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(VaultServiceImpl.class);

    @Value("#{cfg.omdburl}")
    private String omdburl;

    @Value("#{cfg.themoviedburl}")
    private String themoviedburl;

    @Value("#{cfg.videopath}")
    private String videopath;

    @Override
    public GetMovieResponse getMovie(GetMovieRequest parameters) throws BusinessException {

        // check credentials
        if (!auth(parameters.getToken())) {
            GetMovieResponse getMovieResponse = new GetMovieResponse();
            getMovieResponse.setMovie(null);
            getMovieResponse.setResult("401");
            return getMovieResponse;
        }

        GetMovieResponse response = new GetMovieResponse();


        FileDataSource dataSource = new FileDataSource(videopath + parameters.getImdbId());

        if (!(dataSource.getFile().exists() && dataSource.getFile().length() > 0 && dataSource.getFile().canRead())) {
            response.setMovie(null);
            response.setResult("500/movie " + parameters.getImdbId() + " not found");

        } else {
            response.setMovie(new DataHandler(dataSource));
            response.setResult("200");
        }

        return response;
    }

    /**
     * given the movie in byte format it saves it to disk and fetches the movie metadata from other rest services
     * then saves the retrieved information into the db
     * @param parameters payload coming from the soap call
     * @return an AddMovieResponse
     * @throws BusinessException
     */
    @Override
    public AddMovieResponse addMovie(AddMovieRequest parameters) throws BusinessException {

        AddMovieResponse addMovieResponse = new AddMovieResponse();

        // check credentials
        if (!auth(parameters.getToken())) {
            addMovieResponse.setResult("401");
            return addMovieResponse;
        }

        // get data from the request
        DataHandler movieData = parameters.getMovie();
        String imdbId = parameters.getImdbId();

        // create a temporary movie object
        Movie m = new Movie();
        m.setPoster("");
        m.setDirectors("");
        m.setTitle("");
        m.setGenres("");
        m.setRating(0.0);
        m.setImdbId(imdbId);
        m.setStatus("UPLOADING");
        m.setViews(0);

        // the query will return null if ther isn't another entry with the same imdbId into the table "movie"
        // either when the movie is already been added, or the movie is being added in this moment by another user
        Movie mdb = movieRepository.findOneByImdbId(imdbId);
        if (mdb != null) {
            if (mdb.getStatus().equals("UPLOADED")) {
                addMovieResponse.setResult("500/movie already in db");
            } else if (mdb.getStatus().equals("UPLOADING")) {
                addMovieResponse.setResult("500/movie in being uploaded by another user");
            } else {
                addMovieResponse.setResult("500/db is corrupted");
            }
            return addMovieResponse;
        }

        // save temporary entry in db
        movieRepository.save(m);

        // omdb request (for movie metadata)
        try {
            m = getOmdbData(m);
        } catch (Exception e) {
            e.printStackTrace();
            addMovieResponse.setResult("500/omdb rest call failed");
            movieRepository.deleteByImdbId(imdbId);
            deleteVideo(new File(videopath + imdbId));
            return addMovieResponse;
        }
        movieRepository.update(m);

        // themoviedb request (for poster)
        try {
            m = getTheMovieDbData(m);
        } catch (Exception e) {
            e.printStackTrace();
            addMovieResponse.setResult("500/themoviedb rest call failed");
            movieRepository.deleteByImdbId(imdbId);
            deleteVideo(new File(videopath + imdbId));
            return addMovieResponse;
        }
        movieRepository.update(m);

        // save movie to disk
        try {
            saveVideo(movieData, new File(videopath + imdbId));
            LOGGER.info("movie saved.");
        } catch (IOException e) {
            e.printStackTrace();
            movieRepository.deleteByImdbId(imdbId);
            deleteVideo(new File(videopath + imdbId));
            addMovieResponse.setResult("500/can't save file to disk");
            return addMovieResponse;
        }

        m.setStatus("UPLOADED");

        // update details into db
        movieRepository.update(m);

        //TODO send fetchmovie to the least loaded supplier

        // return response
        addMovieResponse.setResult("200");

        LOGGER.info("movie added successfully");

        return addMovieResponse;
    }

    /**
     * utility function that is used to save the movie file
     *
     * @param dataHandler container that contains the data
     * @param filePath    where to save the file
     */
    private static void saveVideo(final DataHandler dataHandler, final File filePath) throws IOException {
        // clean before saving the file
        LOGGER.info("cleaning...");
        deleteVideo(filePath);
        // save the file to disk
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        dataHandler.writeTo(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * utility function that is used to delete the video in case of errors
     * @param file file object to delete
     */
    private static void deleteVideo(final File file) {
        if(file.delete()){
            LOGGER.info("movie "+file.getName()+" successfully deleted.");
        }else{
            LOGGER.warn("something went wrong while deleting the movie "+file.getName());
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

    /**
     * performs a rest call to the omdb service to retrieve the movie metadata given the imdb id
     * @param m the movie object filled with the new data
     * @return the movie object
     * @throws Exception if the call goes wrong
     */
    private Movie getOmdbData(Movie m) throws Exception {

        LOGGER.info("REST request to OMDB.");

        String title;
        String directors;
        String genres;
        Double rating;

        // build the url

        String url = omdburl + m.getImdbId();
        // connect to url
        HttpURLConnection c;

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

        m.setTitle(title);
        m.setDirectors(directors);
        m.setGenres(genres);
        m.setRating(rating);

        return m;
    }

    /**
     * performs a rest call to the themoviedb service to retrieve the movie poster name given the imdb id
     * @param m the movie object filled with the new data
     * @return the movie object
     * @throws Exception if the call goes wrong
     */
    private Movie getTheMovieDbData(Movie m) throws Exception {

        LOGGER.info("REST request to TheMovieDatabase.");

        String poster;

        // build the url

        String url = themoviedburl.replace("@", m.getImdbId());
        // connect to url
        HttpURLConnection c;

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

                // retrieve the poster name
                JsonElement jelement = new JsonParser().parse(sb.toString());
                JsonObject jobject = jelement.getAsJsonObject();
                JsonArray jarray = jobject.getAsJsonArray("movie_results");
                jobject = jarray.get(0).getAsJsonObject();
                poster = jobject.get("poster_path").getAsString();

                /*
                JsonObject jobj = new Gson().fromJson(sb.toString(), JsonObject.class);
                poster = jobj.get("poster_path").getAsString();
                */
                LOGGER.info("movie data retrieved: {}", poster);

                break;

            default:
                if (c != null) {
                    c.disconnect();
                }
                throw new Exception("Http Error: " + status);
        }

        m.setPoster(poster);

        return m;
    }

}
