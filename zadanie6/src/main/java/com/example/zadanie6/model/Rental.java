package com.example.zadanie6.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Rental {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleId;
    private String userId;

    private boolean active = true;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Rental() {}

    public Long getId() {
        return id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    // === SETTERY ===
    public void setId(Long id) {
        this.id = id;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
