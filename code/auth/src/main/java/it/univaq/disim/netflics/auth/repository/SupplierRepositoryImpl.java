package it.univaq.disim.netflics.auth.repository;

import it.univaq.disim.netflics.auth.BusinessException;
import it.univaq.disim.netflics.auth.model.Session;
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
public class SupplierRepositoryImpl implements SupplierRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(SupplierRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    public Boolean findByToken(String token) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        Boolean auth = false;

        String sql = "SELECT * " +
                "FROM supplier " +
                "WHERE token = '" + token + "'";

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);

            if (rs.next()) {

                auth = true;

            } else {

                auth = false;

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
        return auth;
    }
}
