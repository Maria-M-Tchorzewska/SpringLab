package org.example;

public class Admin extends User {

    public Admin(String login, String password) {
        super(login, password, "ADMIN");
    }

    public void addVehicle(IVehicleRepository repository, Vehicle vehicle) {
        repository.addVehicle(vehicle);
        System.out.println("Admin " + getLogin() + " dodał pojazd: " + vehicle);
    }

    public void removeVehicle(IVehicleRepository repository, int vehicleId) {
        boolean removed = repository.removeVehicle(vehicleId);
        if (removed) {
            System.out.println("Admin " + getLogin() + " usunął pojazd o ID: " + vehicleId);
        } else {
            System.out.println("Nie znaleziono pojazdu o ID: " + vehicleId);
        }
    }

    public void listVehicles(IVehicleRepository repository) {
        System.out.println("Lista pojazdów: ");
        for (Vehicle vehicle : repository.getVehicles()) {
            System.out.println(vehicle);
        }
    }

    public void listUsers() {
        System.out.println("Lista użytkowników: ");
        for (User user : User.getUsers()) {
            System.out.println(user);
        }
    }

}
