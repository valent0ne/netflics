package it.univaq.disim.netflics.supplier.model;

import java.sql.Timestamp;

public class Availability implements java.io.Serializable {
    private Long id;
    private Long supplier_id;
    private Timestamp timestamp;
    private Double cpuSaturation;
    private Double memSaturation;
    private Boolean available;

    public Availability() {
        this.id = null;
        this.supplier_id = null;
        this.timestamp = null;
        this.cpuSaturation = null;
        this.memSaturation = null;
        this.available = null;
    }

    public Availability(Long id, Long supplier_id, Timestamp timestamp, Double cpuSaturation, Double memSaturation, Boolean available) {
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

    public Long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Double getCpuSaturation() {
        return cpuSaturation;
    }

    public void setCpuSaturation(Double cpuSaturation) {
        this.cpuSaturation = cpuSaturation;
    }

    public Double getMemSaturation() {
        return memSaturation;
    }

    public void setMemSaturation(Double memSaturation) {
        this.memSaturation = memSaturation;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
