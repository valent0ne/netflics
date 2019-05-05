package it.univaq.disim.netflics.informer.model;

public class Session {
    private Long id;
    private Long userId;
    private String token;

    public Session() {
        this.id = null;
        this.userId = null;
        this.token = null;
    }

    public Session(Long id, Long userId, String token) {
        this.id = id;
        this.userId = userId;
        this.token = token;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }
}
