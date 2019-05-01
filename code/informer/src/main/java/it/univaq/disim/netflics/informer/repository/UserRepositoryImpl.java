package it.univaq.disim.netflics.informer.repository;

import it.univaq.disim.netflics.informer.BusinessException;
import it.univaq.disim.netflics.informer.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@SuppressWarnings("Duplicates")
public class UserRepositoryImpl implements UserRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    public User findOneById(Long id) {

        LOGGER.info("user data: {}", id);

        ResultSet rs;
        User u = new User();

        String sql = "SELECT * FROM user WHERE id = ?";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                u.setId(rs.getLong("id"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
            } else {
                LOGGER.info("user {} is not registered", id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return u;
    }
}
