package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.BusinessException;
import it.univaq.disim.netflics.supplier.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@SuppressWarnings("Duplicates")
public class MovieRepositoryImpl implements MovieRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    public Movie findOneByImdbId(String imdbId){

        LOGGER.info("imdbId: {}", imdbId);

        ResultSet rs;

        Movie movie = new Movie();

        String sql = "SELECT * FROM movie WHERE imdb_id = ?";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, imdbId);
            rs = st.executeQuery();

            if (rs.next()) {
                movie.setId(rs.getLong("id"));
                movie.setTitle(rs.getString("title"));
                movie.setDirectors(rs.getString("directors"));
                movie.setGenres(rs.getString("genres"));
                movie.setRating(rs.getFloat("rating"));
                movie.setImdbId(imdbId);
            }else{
                LOGGER.error("no movie found by the imdbid: {}", imdbId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return movie;

    }

}
