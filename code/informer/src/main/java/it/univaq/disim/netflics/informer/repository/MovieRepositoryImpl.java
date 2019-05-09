package it.univaq.disim.netflics.informer.repository;

import it.univaq.disim.netflics.informer.BusinessException;
import it.univaq.disim.netflics.informer.model.Movie;
import it.univaq.disim.netflics.informer.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Repository
@SuppressWarnings("Duplicates")
public class MovieRepositoryImpl implements MovieRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;


    @Override
    public List<Movie> findAll() throws BusinessException{
        ResultSet rs;
        String sql = "SELECT * FROM movie";

        List<Movie> movies = new ArrayList<>();

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            rs = st.executeQuery();

            while(rs.next()){
                Movie m = new Movie();
                m.setTitle(rs.getString("title"));
                m.setGenres(rs.getString("genres"));
                m.setDirectors(rs.getString("directors"));
                m.setRating(rs.getDouble("rating"));
                m.setPoster(rs.getString("poster"));
                m.setImdbId(rs.getString("imdb_id"));
                m.setViews(rs.getInt("views"));
                movies.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return movies;
    }

    public Movie findOneByImdbId(String imdbId ) throws BusinessException{

        ResultSet rs;

        String sql = "SELECT * FROM movie WHERE imdb_id = ?";

        LOGGER.debug("query: {}", sql);

        Movie m = null;

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, imdbId);
            rs = st.executeQuery();

            if(rs.next()){
                m = new Movie();
                m.setTitle(rs.getString("title"));
                m.setGenres(rs.getString("genres"));
                m.setDirectors(rs.getString("directors"));
                m.setRating(rs.getDouble("rating"));
                m.setPoster(rs.getString("poster"));
                m.setStatus(rs.getString("status"));
                m.setViews(rs.getInt("views"));
                m.setImdbId(imdbId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return m;
    }

    @Override
    public List<Movie> bestOnes(int limit) throws BusinessException{
        ResultSet rs;
        String sql = "SELECT * FROM movie ORDER BY rating DESC LIMIT ?";

        List<Movie> movies = new ArrayList<>();

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, limit);
            rs = st.executeQuery();

            while(rs.next()){
                Movie m = new Movie();
                m.setTitle(rs.getString("title"));
                m.setGenres(rs.getString("genres"));
                m.setDirectors(rs.getString("directors"));
                m.setRating(rs.getDouble("rating"));
                m.setPoster(rs.getString("poster"));
                m.setImdbId(rs.getString("imdb_id"));
                m.setViews(rs.getInt("views"));
                movies.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return movies;
    }

    @Override
    public List<Movie> lastViewed(User u, int limit) throws BusinessException{
        ResultSet rs;
        String sql = "SELECT DISTINCT movie.title as title, " +
                            "movie.genres as genres, " +
                            "movie.directors as directors, " +
                            "movie.rating as rating, " +
                            "movie.poster as poster, " +
                            "movie.views as views, " +
                            "movie.imdb_id as imdb_id " +
                      "FROM user_movie LEFT JOIN movie " +
                            "ON user_movie.movie_id = movie.id " +
                      "WHERE user_movie.user_id = ? " +
                      "LIMIT ?";

        List<Movie> movies = new ArrayList<>();

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, u.getId());
            st.setInt(2, limit);
            rs = st.executeQuery();

            while(rs.next()){
                Movie m = new Movie();
                m.setTitle(rs.getString("title"));
                m.setGenres(rs.getString("genres"));
                m.setDirectors(rs.getString("directors"));
                m.setRating(rs.getDouble("rating"));
                m.setPoster(rs.getString("poster"));
                m.setImdbId(rs.getString("imdb_id"));
                m.setViews(rs.getInt("views"));

                movies.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }

        Collections.reverse(movies);

        return movies;
    }

    @Override
    public List<Movie> mostViewed(int limit) throws BusinessException{
        ResultSet rs;
        String sql = "SELECT * FROM movie ORDER BY views DESC LIMIT ?";

        List<Movie> movies = new ArrayList<>();

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, limit);
            rs = st.executeQuery();

            while(rs.next()){
                Movie m = new Movie();
                m.setTitle(rs.getString("title"));
                m.setGenres(rs.getString("genres"));
                m.setDirectors(rs.getString("directors"));
                m.setRating(rs.getDouble("rating"));
                m.setPoster(rs.getString("poster"));
                m.setImdbId(rs.getString("imdb_id"));
                m.setViews(rs.getInt("views"));
                movies.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return movies;
    }




}
