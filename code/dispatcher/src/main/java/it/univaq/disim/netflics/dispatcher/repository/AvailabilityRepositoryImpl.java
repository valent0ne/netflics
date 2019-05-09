package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.Availability;
import it.univaq.disim.netflics.dispatcher.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@SuppressWarnings("Duplicates")
public class AvailabilityRepositoryImpl implements AvailabilityRepository{

    private static Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    public HashMap<Supplier, Availability> findRecent(int threshold) throws BusinessException{

        HashMap<Supplier, Availability> recentAvailability = new HashMap<>();

        ResultSet rs;

        String sql = "SELECT a1.supplier_id, " +
                            "s.ip, " +
                            "s.port, " +
                            "s.token, " +
                            "a1.id, " +
                            "a1.timestamp, " +
                            "a1.cpu_saturation, " +
                            "a1.mem_saturation, " +
                            "a1.available " +
                     "FROM supplier as s JOIN availability as a1 " +
                     "ON s.id = a1.supplier_id " +
                     "WHERE timestamp = (SELECT MAX(timestamp) " +
                                        "FROM availability as a2 " +
                                        "WHERE a1.supplier_id = a2.supplier_id AND timestamp > NOW() - ?) AND available = 1 "+
                     "ORDER BY supplier_id, timestamp";
        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, threshold);
            rs = st.executeQuery();

            while(rs.next()){
                Availability a = new Availability();
                Supplier s = new Supplier();
                s.setId(rs.getLong("supplier_id"));
                s.setIp(rs.getString("ip"));
                s.setPort(rs.getString("port"));
                s.setToken(rs.getString("token"));
                a.setId(rs.getLong("id"));
                a.setSupplier_id(rs.getLong("supplier_id"));
                a.setTimestamp(rs.getTimestamp("timestamp"));
                a.setCpuSaturation(rs.getDouble("cpu_saturation"));
                a.setMemSaturation(rs.getDouble("mem_saturation"));
                a.setAvailable(rs.getBoolean("available"));
                recentAvailability.put(s,a);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return recentAvailability;
    }

    public void setUnavailable(Supplier s) throws BusinessException{

        int rs;

        String sql = "INSERT INTO availability (supplier_id, timestamp, cpu_saturation, mem_saturation, available) VALUES (?, ?, ?, ?, ?)";

        LOGGER.debug("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, s.getId());
            st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            st.setDouble(3, 0.0);
            st.setDouble(4, 0.0);
            st.setBoolean(5, false);

            rs = st.executeUpdate();

            if(rs != 1){
                throw new SQLException("query failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
    }

}
