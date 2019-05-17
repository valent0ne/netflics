package it.univaq.disim.netflics.supplier.model;

import java.util.Objects;

public class Supplier {

    private Long id;
    private String ip;
    private String port;
    private String token;
    private String status;


    public Supplier() {
        this.id = null;
        this.ip = null;
        this.port = null;
        this.token = null;
        this.status = null;
    }


    public Supplier(Long id, String ip, String port, String token, String status) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        this.token = token;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(id, supplier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
