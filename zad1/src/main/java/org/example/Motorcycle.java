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
        System.out.println("Motorcycle - " + toString() + ", Category: " + category);
    }

    @Override
    public String toCSV() {
        return super.toCSV() + ";" + category;
    }

    @Override
    public String toString() {
        return super.toString() + ", Category: " + category;
    }
}
