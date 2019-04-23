package it.univaq.disim.netflics.vault.repository;

import it.univaq.disim.netflics.vault.BusinessException;
import it.univaq.disim.netflics.vault.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@Repository
public class MovieRepositoryImpl implements MovieRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    public Movie save(Movie movie) {

        LOGGER.info("movie data: {} {} {} {}", movie.getTitle(),
                movie.getDirectors(),
                movie.getGenres(),
                movie.getRating(),
                movie.getImdbId());

        Connection con = null;
        Statement st = null;
        Integer rs = null;

        String sql = String.format("INSERT INTO movie (title, directors, genres, rating, imdb_id) VALUES ('%s', '%s', '%s', %s, '%s')", movie.getTitle(), movie.getDirectors(), movie.getGenres(), movie.getRating(), movie.getImdbId());

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
        return movie;
    }
}
