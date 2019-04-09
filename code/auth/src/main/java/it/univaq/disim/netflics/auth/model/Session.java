package it.univaq.disim.netflics.auth.model;

public class Session {
    private Long id;
    private Long userId;
    private String token;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }
}
