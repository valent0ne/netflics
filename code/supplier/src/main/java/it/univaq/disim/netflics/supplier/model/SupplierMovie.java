package it.univaq.disim.netflics.supplier.model;

public class SupplierMovie implements java.io.Serializable {
    private Long id;
    private Long supplierId;
    private Long movieId;
    private String status;

    public SupplierMovie() {
        this.id = null;
        this.supplierId = null;
        this.movieId = null;
    }

    public SupplierMovie(Long id, Long supplierId, Long movieId) {
        this.id = id;
        this.supplierId = supplierId;
        this.movieId = movieId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
