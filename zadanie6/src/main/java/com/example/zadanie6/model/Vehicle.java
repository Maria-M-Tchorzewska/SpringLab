package com.example.zadanie6.model;

import jakarta.persistence.*;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private boolean active = true;
    private boolean rented = false;

    public Vehicle() {}

    public Vehicle(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isRented() {
        return rented;
    }

    // === SETTERY ===

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
}
