package it.univaq.disim.netflics.vault.repository;

import it.univaq.disim.netflics.vault.BusinessException;
import it.univaq.disim.netflics.vault.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


@Repository
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

        Integer rs = null;

        String sql = "INSERT INTO movie (title, directors, genres, rating, imdb_id) VALUES (?, ?, ?, ?, ?)";

        LOGGER.info("query: {}", sql);

        try (Connection con = dataSource.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, movie.getTitle());
            st.setString(2, movie.getDirectors());
            st.setString(3, movie.getGenres());
            st.setDouble(4, movie.getRating());
            st.setString(5, movie.getImdbId());

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
}
