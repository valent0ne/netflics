package it.univaq.disim.netflics.informer.model;

public class User implements java.io.Serializable {
    private Long id;
    private String email;
    private String password;
    private String role;

    public User() {
        this.id = null;
        this.email = null;
        this.password = null;
        this.role = null;
    }

    public User(Long id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }
}
