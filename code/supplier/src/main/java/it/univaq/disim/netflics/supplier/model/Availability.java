package it.univaq.disim.netflics.supplier.model;

import java.sql.Timestamp;

public class Availability implements java.io.Serializable {
    private Long id;
    private String supplier_id;
    private Timestamp timestamp;
    private Integer cpuSaturation;
    private Integer memSaturation;
    private Boolean available;

    public Availability() {
        this.id = null;
        this.supplier_id = null;
        this.timestamp = null;
        this.cpuSaturation = null;
        this.memSaturation = null;
        this.available = null;
    }

    public Availability(Long id, String supplier_id, Timestamp timestamp, Integer cpuSaturation, Integer memSaturation, Boolean available) {
        this.id = id;
        this.supplier_id = supplier_id;
        this.timestamp = timestamp;
        this.cpuSaturation = cpuSaturation;
        this.memSaturation = memSaturation;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCpuSaturation() {
        return cpuSaturation;
    }

    public void setCpuSaturation(Integer cpuSaturation) {
        this.cpuSaturation = cpuSaturation;
    }

    public Integer getMemSaturation() {
        return memSaturation;
    }

    public void setMemSaturation(Integer memSaturation) {
        this.memSaturation = memSaturation;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
