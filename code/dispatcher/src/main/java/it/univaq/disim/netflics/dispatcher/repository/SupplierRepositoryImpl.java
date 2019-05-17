package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.dispatcher.BusinessException;
import it.univaq.disim.netflics.dispatcher.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("Duplicates")
public class SupplierRepositoryImpl implements SupplierRepository{

    private static Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;


    public List<Supplier> findAll() throws BusinessException {

        ResultSet rs;
        String sql = "SELECT * FROM supplier";

        List<Supplier> suppliers = new ArrayList<>();

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            rs = st.executeQuery();

            while(rs.next()){
                Supplier s = new Supplier();
                s.setId(rs.getLong("id"));
                s.setIp(rs.getString("ip"));
                s.setPort(rs.getString("port"));
                s.setToken(rs.getString("token"));
                suppliers.add(s);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return suppliers;
    }

    public List<Supplier> findAllByMovieFetched(String imdbId) throws BusinessException {

        ResultSet rs;

        String sql = "SELECT s.id, s.ip, s.port, s.token, s.slots " +
                     "FROM supplier as s " +
                     "JOIN supplier_movie as sm " +
                        "ON s.id = sm.supplier_id " +
                     "JOIN movie as m " +
                        "ON sm.movie_id = m.id " +
                     "WHERE m.imdb_id = ? AND sm.status = 'FETCHED'";

        List<Supplier> suppliers = new ArrayList<>();

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1,imdbId);
            rs = st.executeQuery();

            while(rs.next()){
                Supplier s = new Supplier();
                s.setId(rs.getLong("id"));
                s.setIp(rs.getString("ip"));
                s.setPort(rs.getString("port"));
                s.setToken(rs.getString("token"));
                s.setSlots(rs.getInt("slots"));
                suppliers.add(s);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return suppliers;
    }

    public List<Supplier> findAllByMovieFetching(String imdbId) throws BusinessException {

        ResultSet rs;

        String sql = "SELECT s.id, s.ip, s.port, s.token, s.slots " +
                     "FROM supplier as s " +
                     "JOIN supplier_movie as sm " +
                        "ON s.id = sm.supplier_id " +
                     "JOIN movie as m " +
                        "ON sm.movie_id = m.id " +
                     "WHERE m.imdb_id = ? AND sm.status = 'FETCHING'";

        List<Supplier> suppliers = new ArrayList<>();

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1,imdbId);
            rs = st.executeQuery();

            while(rs.next()){
                Supplier s = new Supplier();
                s.setId(rs.getLong("id"));
                s.setIp(rs.getString("ip"));
                s.setPort(rs.getString("port"));
                s.setToken(rs.getString("token"));
                s.setSlots(rs.getInt("slots"));
                suppliers.add(s);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return suppliers;
    }




}
