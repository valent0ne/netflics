package it.univaq.disim.netflics.auth.repository;

import it.univaq.disim.netflics.auth.BusinessException;
import it.univaq.disim.netflics.auth.model.Session;
import it.univaq.disim.netflics.auth.model.User;
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

    @Autowired
    private DataSource dataSource;


    public Session findByToken(String token) {

        ResultSet rs;

        Session s = new Session();

        String sql = "SELECT * FROM session WHERE token = ?";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, token);
            rs = st.executeQuery();

            if (rs.next()) {

                s.setId(rs.getLong("id"));
                s.setToken(rs.getString("token"));
                s.setUserId(rs.getLong("user_id"));

            } else {
                LOGGER.info("Token not valid");
                s = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return s;
    }

    public Session save(Session s) {

        int rs;

        String sql = "INSERT INTO session (user_id, token) VALUES (?, ?)";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, s.getUserId());
            st.setString(2, s.getToken());
            rs = st.executeUpdate();

            if(rs != 1){
                throw new SQLException("query failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return s;
    }

    public void deleteAll (User u){

        int rs;

        String sql = "DELETE FROM session WHERE user_id = ?";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, u.getId());
            rs = st.executeUpdate();
            LOGGER.info("{} rows deleted", rs);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }

    }

    public void delete (String token){

        int rs;

        String sql = "DELETE FROM session WHERE token = ?";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, token);
            rs = st.executeUpdate();
            LOGGER.info("{} rows deleted", rs);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }

    }

    public String generateToken(){

        ResultSet rs;

        String sql = "SELECT UUID()";

        String token = "";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            rs = st.executeQuery();

            if (rs.next()) {
                token = rs.getString("UUID()");
            } else {
                LOGGER.error("could not generate token");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return token;
    }
}
