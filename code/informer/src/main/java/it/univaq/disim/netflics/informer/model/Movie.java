package it.univaq.disim.netflics.informer.model;

public class Movie implements java.io.Serializable {
    private Long id;
    private String title;
    private String directors;
    private String genres;
    private Double rating;
    private String imdbId;
    private String status;
    private String poster;

    public Movie() {
        this.id = null;
        this.title = null;
        this.directors = null;
        this.genres = null;
        this.rating = null;
        this.imdbId = null;
        this.status = null;
        this.poster = null;
    }

    public Movie(Long id, String title, String directors, String genres, Double rating, String imdbId, String status, String poster) {
        this.id = id;
        this.title = title;
        this.directors = directors;
        this.genres = genres;
        this.rating = rating;
        this.imdbId = imdbId;
        this.status = status;
        this.poster = poster;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
