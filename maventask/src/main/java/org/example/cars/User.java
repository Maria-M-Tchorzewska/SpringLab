package org.example.cars;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static final List<User> users = new ArrayList<>();

    private String login;
    private String password;
    private String role;
    private Vehicle rentedCar;

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.rentedCar = null;
        users.add(this);
    }

    public static List<User> getUsers() {
        return users;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public Vehicle getRentedCar() {
        return rentedCar;
    }

    public void rentCar(Vehicle car) {
        if (this.rentedCar == null) {
            this.rentedCar = car;
            car.setRented(true);
            System.out.println("Użytkownik " + login + " wypożyczył pojazd: " + car);
        } else {
            System.out.println("Użytkownik " + login + " już posiada wypożyczony pojazd.");
        }
    }

    public void returnCar() {
        if (this.rentedCar != null) {
            this.rentedCar.setRented(false);
            System.out.println("Użytkownik " + login + " zwrócił pojazd: " + rentedCar);
            this.rentedCar = null;
        } else {
            System.out.println("Użytkownik " + login + " nie ma wypożyczonego pojazdu.");
        }
    }

    @Override
    public String toString() {
        return "User{login='" + login + "', role='" + role + "', rentedCar=" + (rentedCar != null ? rentedCar.getId() : "brak") + "}";
    }

    public String getPassword() {
        return password;
    }
}
