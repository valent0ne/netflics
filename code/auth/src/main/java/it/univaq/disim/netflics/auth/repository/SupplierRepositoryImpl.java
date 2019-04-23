package it.univaq.disim.netflics.auth.repository;

import it.univaq.disim.netflics.auth.BusinessException;
import it.univaq.disim.netflics.auth.model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@SuppressWarnings("Duplicates")
public class SupplierRepositoryImpl implements SupplierRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(SupplierRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    public Boolean findByToken(String token) {

        ResultSet rs;

        boolean auth = false;

        String sql = "SELECT * FROM supplier WHERE token = ?";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, token);
            rs = st.executeQuery();

            if (rs.next()) {
                auth = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return auth;
    }
}
