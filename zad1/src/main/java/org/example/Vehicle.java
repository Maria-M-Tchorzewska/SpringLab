package org.example;

public abstract class Vehicle {
    private static int idCounter = 1;
    //id jednoznacznie odrozniajac pojazdy
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

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public boolean isRented() {
        return rented;
    }

    public void rent() {
        if (!rented) {
            rented = true;
            System.out.println("Pojazd " + brand + " " + model + " został wynajęty.");
        } else {
            System.out.println("Pojazd " + brand + " " + model + " jest już wynajęty.");
        }
    }

    public void returnVehicle() {
        if (rented) {
            rented = false;
            System.out.println("Pojazd " + brand + " " + model + " został zwrócony.");
        } else {
            System.out.println("Pojazd " + brand + " " + model + " nie był wynajęty.");
        }
    }

    public abstract void displayInfo();

    public String toCSV() {
        return id + ";" + brand + ";" + model + ";" + year + ";" + price + ";" + rented;
    }

    public void setRented(boolean rented) { this.rented = rented; }

    @Override
    public String toString() {
        return "Vehicle ID: " + id +
                ", Brand: " + brand +
                ", Model: " + model +
                ", Year: " + year +
                ", Price: " + price +
                ", Rented: " + (rented ? "Yes" : "No");
    }
}

