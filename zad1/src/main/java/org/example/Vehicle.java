package org.example;

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
}
