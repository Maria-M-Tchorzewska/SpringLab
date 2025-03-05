package org.example;

public class Motorcycle extends Vehicle {
    private String category;

    public Motorcycle(String brand, String model, int year, double price, String category) {
        super(brand, model, year, price);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void displayInfo() {
        System.out.println(toString());
    }

    @Override
    public String toCSV() {
        return id + ";" + brand + ";" + model + ";" + year + ";" + price + ";" + rented + ";Motorcycle;" + category;
    }

    @Override
    public String toString() {
        return "Motorcycle - ID: " + id + ", " + brand + " " + model + ", " + year + ", $" + price + ", Rented: " + (rented ? "Yes" : "No") + ", Category: " + category;
    }
}
