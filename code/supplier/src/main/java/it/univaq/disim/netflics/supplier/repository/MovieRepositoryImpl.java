package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.BusinessException;
import it.univaq.disim.netflics.supplier.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
@SuppressWarnings("Duplicates")
public class MovieRepositoryImpl implements MovieRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    public Movie findOneByImdbId(String imdbId){

        LOGGER.info("imdbId: {}", imdbId);

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        Movie movie = new Movie();

        String sql = String.format("SELECT * FROM movie WHERE imdb_id = '%s'", imdbId);

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {
                movie.setId(rs.getLong("id"));
                movie.setTitle(rs.getString("title"));
                movie.setDirectors(rs.getString("directors"));
                movie.setGenres(rs.getString("genres"));
                movie.setRating(rs.getFloat("rating"));
                movie.setImdbId(imdbId);
            }

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
