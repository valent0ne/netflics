package it.univaq.disim.netflics.dispatcher.repository;

import it.univaq.disim.netflics.dispatcher.model.Movie;
import it.univaq.disim.netflics.dispatcher.BusinessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;


@Repository
@SuppressWarnings("Duplicates")
public class MovieRepositoryImpl implements MovieRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryImpl.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    public Movie updateViews(Movie movie) throws BusinessException{

        LOGGER.debug("movie data: {} {} {} {} {}", movie.getTitle(),
                movie.getDirectors(),
                movie.getGenres(),
                movie.getRating(),
                movie.getImdbId());

        int rs;

        String sql = "UPDATE movie SET views = views+1 WHERE imdb_id = ?";

        LOGGER.debug("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, movie.getImdbId());
            rs = st.executeUpdate();

            if(rs != 1){
                throw new SQLException("query failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return movie;
    }

    public Movie findOneByImdbId(String imdbId ) throws BusinessException{

        ResultSet rs;

        String sql = "SELECT * FROM movie WHERE imdb_id = ?";

        LOGGER.debug("query: {}", sql);

        it.univaq.disim.netflics.dispatcher.model.Movie m = null;

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, imdbId);
            rs = st.executeQuery();

            if(rs.next()){
                m = new it.univaq.disim.netflics.dispatcher.model.Movie();
                m.setId(rs.getLong("id"));
                m.setTitle(rs.getString("title"));
                m.setGenres(rs.getString("genres"));
                m.setDirectors(rs.getString("directors"));
                m.setRating(rs.getDouble("rating"));
                m.setPoster(rs.getString("poster"));
                m.setStatus(rs.getString("status"));
                m.setViews(rs.getInt("views"));
                m.setImdbId(imdbId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return m;
    }
}
