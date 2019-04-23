package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.model.Availability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import it.univaq.disim.netflics.vault.BusinessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


@Repository
public class AvailabilityRepositoryImpl implements AvailabilityRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(AvailabilityRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    public Availability save(Availability availability){

        Connection con = null;
        Statement st = null;
        Integer rs = null;

        String sql = String.format("INSERT INTO availability (supplier_id, timestamp, cpu_saturation, mem_saturation, available) VALUES (%d, '%s', '%s', '%s', '%s') ", availability.getSupplier_id(), availability.getTimestamp(), availability.getCpuSaturation(), availability.getMemSaturation(), availability.getAvailable());

        LOGGER.info("query: {}", sql);

        try {
            con = dataSource.getConnection();
            st = con.createStatement();
            rs = st.executeUpdate(sql);

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
        return availability;
    }


}