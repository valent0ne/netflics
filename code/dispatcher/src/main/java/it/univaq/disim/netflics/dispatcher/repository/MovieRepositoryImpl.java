package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.clients.informer.Movie;
import it.univaq.disim.netflics.dispatcher.BusinessException;

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

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    public Movie updateViews(Movie movie){

        LOGGER.debug("movie data: {} {} {} {} {}", movie.getTitle(),
                movie.getDirectors(),
                movie.getGenres(),
                movie.getRating(),
                movie.getImdbId());

        int rs;

        String sql = "UPDATE movie SET views = views+1 WHERE imdb_id = ?";

        LOGGER.debug("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, movie.getImdbId());
            rs = st.executeUpdate();

            if(rs != 1){
                throw new SQLException("query failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return movie;
    }
}
