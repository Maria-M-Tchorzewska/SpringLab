package org.example.cars;

public class Admin extends User {

    public Admin(String id, String login, String password) {
        super(id, login, password, "ADMIN");
    }

    public void addVehicle(IVehicleRepository repo, Vehicle vehicle) {
        vehicle.addAttribute("rented", false);
        repo.addVehicle(vehicle);
        System.out.println("Admin " + getLogin() + " dodał pojazd: " + vehicle);
    }

    public void removeVehicle(IVehicleRepository vehicleRepo, int vehicleId) {
        boolean removed = vehicleRepo.removeVehicle(vehicleId);
        if (removed) {
            System.out.println("Admin " + getLogin() + " usunął pojazd o ID: " + vehicleId);
        } else {
            System.out.println("Nie znaleziono pojazdu o ID: " + vehicleId);
        }
    }

    public void listAllVehicles(IVehicleRepository vehicleRepo) {
        System.out.println("Wszystkie pojazdy:");
        for (Vehicle v : vehicleRepo.getVehicles()) {
            System.out.println(v);
        }
    }

    public void listUsers(IUserRepository userRepo) {
        System.out.println("Lista użytkowników:");
        for (User user : userRepo.getUsers()) {
            System.out.println(user);
        }
    }
}
