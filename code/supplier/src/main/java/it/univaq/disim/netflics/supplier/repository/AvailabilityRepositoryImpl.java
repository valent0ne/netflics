package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.model.Availability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import it.univaq.disim.netflics.supplier.BusinessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Repository
public class AvailabilityRepositoryImpl implements AvailabilityRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(AvailabilityRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    public Availability save(Availability availability) {

        int rs;

        String sql = "INSERT INTO availability (supplier_id, timestamp, cpu_saturation, mem_saturation, available) VALUES (?, ?, ?, ?, ?) ";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

            st.setLong(1, availability.getSupplier_id());
            st.setTimestamp(2, availability.getTimestamp());
            st.setDouble(3, availability.getCpuSaturation());
            st.setDouble(4, availability.getMemSaturation());
            st.setBoolean(5, availability.getAvailable());
            rs = st.executeUpdate();

            if(rs != 1){
                throw new SQLException("query failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return availability;
    }


}
