package org.example.cars;


public class Car extends Vehicle {
    public Car(String brand, String model, int year, double price) {
        super(brand, model, year, price);
    }

    @Override
    public String toCSV() {
        return id + ";" + brand + ";" + model + ";" + year + ";" + price + ";" + rented + ";Car";
    }

    @Override
    public String toString() {
        return "Car - ID: " + id + ", " + brand + " " + model + ", " + year + ", $" + price + ", Rented: " + (rented ? "Yes" : "No");
    }

    @Override
    public void displayInfo() {
        System.out.println(toString());
    }
}
