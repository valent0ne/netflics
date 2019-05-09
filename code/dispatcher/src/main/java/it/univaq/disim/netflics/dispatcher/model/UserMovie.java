package it.univaq.disim.netflics.dispatcher.model;

public class UserMovie implements java.io.Serializable {
    private Long id;
    private Long user_id;
    private Long movie_id;

    public UserMovie() {
        this.id = null;
        this.user_id = null;
        this.movie_id = null;
    }

    public UserMovie(Long id, Long user_id, Long movie_id) {
        this.id = id;
        this.user_id = user_id;
        this.movie_id = movie_id;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }
}
