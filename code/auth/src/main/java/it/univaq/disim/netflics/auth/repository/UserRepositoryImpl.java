package it.univaq.disim.netflics.auth.repository;

import it.univaq.disim.netflics.auth.BusinessException;
import it.univaq.disim.netflics.auth.model.User;
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

    @Autowired
    private DataSource dataSource;

    public User findOne(User user) {

        LOGGER.debug("user data: {}", user.getEmail());

        ResultSet rs;
        User u = new User();

        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        LOGGER.debug("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, user.getEmail());
            st.setString(2, user.getPassword());

            rs = st.executeQuery();

            if (rs.next()) {
                u.setId(rs.getLong("id"));
                u.setEmail(user.getEmail());
                u.setPassword(user.getPassword());
                u.setRole(rs.getString("role"));
            } else {
                LOGGER.info("user {} is not registered", user.getEmail());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return u;
    }

    public User findOneById(Long id) {

        LOGGER.debug("user data: {}", id);

        ResultSet rs;
        User u = new User();

        String sql = "SELECT * FROM user WHERE id = ?";

        LOGGER.debug("query: {}", sql);

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
