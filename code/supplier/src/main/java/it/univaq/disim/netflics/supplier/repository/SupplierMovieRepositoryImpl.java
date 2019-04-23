package it.univaq.disim.netflics.supplier.repository;

import it.univaq.disim.netflics.supplier.BusinessException;
import it.univaq.disim.netflics.supplier.model.Movie;
import it.univaq.disim.netflics.supplier.model.SupplierMovie;
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
public class SupplierMovieRepositoryImpl implements SupplierMovieRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(SupplierMovieRepositoryImpl.class);

    @Autowired
    private DataSource dataSource;

    public SupplierMovie save(SupplierMovie sm) {

        LOGGER.info("suppliermovie data: {} {} ", sm.getMovieId(), sm.getSupplierId());

        Connection con = null;
        Statement st = null;
        Integer rs = null;

        String sql = String.format("INSERT INTO supplier_movie (supplier_id, movie_id) VALUES (%d, %d)", sm.getMovieId(), sm.getSupplierId());

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
        return sm;
    }

    public void delete (SupplierMovie sm){
        Connection con = null;
        Statement st = null;
        Integer rs = null;

        String sql = String.format("DELETE FROM supplier_movie WHERE movie_id = %d AND supplier_id = %d", sm.getMovieId(), sm.getSupplierId());

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
    }


}
