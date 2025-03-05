package org.example;

public class Car extends Vehicle{
    public Car(String brand, String model, int year, double price) {
        super(brand, model, year, price);
    }

    @Override
    public String toCSV() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void displayInfo() {
        System.out.println("Car - " + toString());
    }
}
