package it.univaq.disim.netflics.auth.repository;

import it.univaq.disim.netflics.auth.BusinessException;
import it.univaq.disim.netflics.auth.model.User;
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
public class UserRepositoryImpl implements UserRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    public User findOne(User user) {

        LOGGER.info("user data: {}",
                user.getEmail());

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        User u = new User();

        String sql = "SELECT *" +
                     "FROM user"+
                     "WHERE email = '"+user.getEmail()+"' "+
                     "AND password = '"+user.getPassword()+"'";

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if(rs.next()){
                u.setId(rs.getLong("id"));
                u.setEmail(user.getEmail());
                u.setPassword(user.getPassword());
                u.setRole(rs.getString("role"));
            }else{

                LOGGER.info("user {} is not registered",
                        user.getEmail());
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
        return u;
    }

    public User findOneById(Long id) {

        LOGGER.info("user data: {}", id);

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        User u = new User();

        String sql = "SELECT *" +
                "FROM user"+
                "WHERE id = "+id;

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if(rs.next()){
                u.setId(rs.getLong("id"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
            }else{

                LOGGER.info("user {} is not registered",
                        id);
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
        return u;
    }
}
