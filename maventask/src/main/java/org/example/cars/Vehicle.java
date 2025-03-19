package org.example.cars;

import java.util.Objects;

public abstract class Vehicle {
    private static int idCounter = 1;
    protected final int id;
    protected String brand;
    protected String model;
    protected int year;
    protected double price;
    protected boolean rented;

    public Vehicle(String brand, String model, int year, double price) {
        this.id = idCounter++;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.rented = false;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public boolean isRented() { return rented; }

    protected void setRented(boolean rented) { this.rented = rented; }

    public abstract String toCSV();
    public abstract String toString();
    public abstract void displayInfo();

    // equals() - porównuje pojazdy na podstawie ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false; // Sprawdzenie typu

        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id;
    }

    //hashCode() - opiera się na id
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
