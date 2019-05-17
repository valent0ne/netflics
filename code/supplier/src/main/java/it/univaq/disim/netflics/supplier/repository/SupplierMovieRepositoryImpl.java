package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.BusinessException;
import it.univaq.disim.netflics.supplier.model.SupplierMovie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@SuppressWarnings("Duplicates")
public class SupplierMovieRepositoryImpl implements SupplierMovieRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(SupplierMovieRepositoryImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    public SupplierMovie save(SupplierMovie sm) throws BusinessException{

        LOGGER.debug("suppliermovie data: {} {} {}", sm.getMovieId(), sm.getSupplierId(), sm.getStatus());

        int rs;

        String sql = "INSERT INTO supplier_movie (supplier_id, movie_id, status) VALUES (?, ?, ?)";

        LOGGER.debug("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, sm.getSupplierId());
            st.setLong(2, sm.getMovieId());
            st.setString(3, sm.getStatus());
            rs = st.executeUpdate();

            if(rs != 1){
                throw new SQLException("query failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return sm;
    }

    public SupplierMovie update(SupplierMovie sm) throws BusinessException{

        LOGGER.debug("suppliermovie data: {} {} {}", sm.getMovieId(), sm.getSupplierId(), sm.getStatus());

        int rs;

        String sql = "UPDATE supplier_movie SET status = ? WHERE movie_id = ? AND supplier_id = ?";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, sm.getStatus());
            st.setLong(2, sm.getMovieId());
            st.setLong(3, sm.getSupplierId());
            rs = st.executeUpdate();

            if(rs != 1){
                throw new SQLException("query failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return sm;
    }

    public void delete (SupplierMovie sm)throws BusinessException{
        int rs;

        String sql = "DELETE FROM supplier_movie WHERE movie_id = ? AND supplier_id = ?";
        LOGGER.debug("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, sm.getMovieId());
            st.setLong(2, sm.getSupplierId());

            rs = st.executeUpdate();

            if (rs != 1) {
                LOGGER.error("query failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
    }

    public void deleteAllBySupplierId (Long supplierId)throws BusinessException{
        int rs;

        String sql = "DELETE FROM supplier_movie WHERE supplier_id = ?";
        LOGGER.debug("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setLong(1, supplierId);

            rs = st.executeUpdate();

            if (rs != 1) {
                LOGGER.error("query failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
    }


}
