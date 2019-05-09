package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;


@SuppressWarnings("Duplicates")
@Repository
public class SessionRepositoryImpl implements SessionRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;


    public Session findByToken(String token) throws BusinessException{

        ResultSet rs;

        Session s = new Session();

        String sql = "SELECT * FROM session WHERE token = ?";

        LOGGER.debug("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, token);
            rs = st.executeQuery();

            if (rs.next()) {

                s.setId(rs.getLong("id"));
                s.setToken(rs.getString("token"));
                s.setUserId(rs.getLong("user_id"));

            } else {
                LOGGER.info("Token does not belong to an user/token not valid");
                s = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return s;
    }
}
