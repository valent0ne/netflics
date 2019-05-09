package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.clients.informer.Movie;
import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.UserMovie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Repository
@SuppressWarnings("Duplicates")
public class UserMovieRepositoryImpl implements UserMovieRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(UserMovieRepositoryImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    public UserMovie save(UserMovie um) throws BusinessException{

        int rs;

        String sql = "INSERT INTO user_movie (user_id, movie_id) VALUES (?, ?)";

        LOGGER.debug("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, um.getUser_id());
            st.setLong(2, um.getMovie_id());

            rs = st.executeUpdate();

            if(rs != 1){
                throw new SQLException("query failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return um;
    }
}
