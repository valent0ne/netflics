package it.univaq.disim.netflics.vault.repository;

import it.univaq.disim.netflics.vault.BusinessException;
import it.univaq.disim.netflics.vault.model.Movie;
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

    @Autowired
    private DataSource dataSource;

    public Movie save(Movie movie) {

        LOGGER.info("movie data: {} {} {} {} {}", movie.getTitle(),
                movie.getDirectors(),
                movie.getGenres(),
                movie.getRating(),
                movie.getImdbId());

        int rs;

        String sql = "INSERT INTO movie (title, directors, genres, rating, imdb_id, poster, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, movie.getTitle());
            st.setString(2, movie.getDirectors());
            st.setString(3, movie.getGenres());
            st.setDouble(4, movie.getRating());
            st.setString(5, movie.getImdbId());
            st.setString(6, movie.getPoster());
            st.setString(7, movie.getStatus());

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

    public Movie update(Movie movie) {

        int rs;

        String sql = "UPDATE movie SET title = ?, directors = ?, genres = ?, rating = ?, poster = ?, status = ? WHERE imdb_id = ?";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, movie.getTitle());
            st.setString(2, movie.getDirectors());
            st.setString(3, movie.getGenres());
            st.setDouble(4, movie.getRating());
            st.setString(5, movie.getPoster());
            st.setString(6, movie.getStatus());
            st.setString(7, movie.getImdbId());


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

    public Movie findOneByImdbId(String imdbId ) {

        ResultSet rs;

        String sql = "SELECT * FROM movie WHERE imdb_id = ?";

        LOGGER.info("query: {}", sql);

        Movie m = null;

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, imdbId);
            rs = st.executeQuery();

            if(rs.next()){
                m = new Movie();
                m.setTitle(rs.getString("title"));
                m.setGenres(rs.getString("genres"));
                m.setDirectors(rs.getString("directors"));
                m.setRating(rs.getDouble("rating"));
                m.setPoster(rs.getString("poster"));
                m.setStatus(rs.getString("status"));
                m.setImdbId(imdbId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return m;
    }

    public void deleteByImdbId(String imdbId) {

        int rs;

        String sql = "DELETE FROM movie WHERE imdb_id = ?";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, imdbId);

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
