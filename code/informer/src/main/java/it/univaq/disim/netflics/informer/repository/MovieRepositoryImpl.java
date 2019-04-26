package it.univaq.disim.netflics.informer.repository;

import it.univaq.disim.netflics.informer.BusinessException;
import it.univaq.disim.netflics.informer.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
@SuppressWarnings("Duplicates")
public class MovieRepositoryImpl implements MovieRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;


    @Override
    public List<Movie> findAll() {
        ResultSet rs;
        String sql = "SELECT * FROM movie";

        List<Movie> movies = new ArrayList<Movie>();

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
                movies.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return movies;
    }

    public Movie findOneByImdbId(String imdbId ) {

        ResultSet rs;

        String sql = "SELECT * FROM movie WHERE imdb_id = ?";

        LOGGER.info("query: {}", sql);

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
                m.setImdbId(imdbId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return m;
    }


}
