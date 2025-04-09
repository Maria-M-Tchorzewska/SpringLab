package org.example.cars;

public class BasicUser extends User {

    private final RentalRepository rentalRepository;
    private final IVehicleRepository vehicleRepository;

    public BasicUser(String id, String login, String password, IVehicleRepository vehicleRepository, RentalRepository rentalRepository) {
        super(id, login, password, "USER");
        this.vehicleRepository = vehicleRepository;
        this.rentalRepository = rentalRepository;
    }

    public void rentVehicle(int vehicleId) {
        Vehicle v = vehicleRepository.getVehicleById(vehicleId);
        if (v == null) {
            System.out.println("Pojazd nie istnieje.");
            return;
        }

        if ("true".equals(String.valueOf(v.getAttribute("rented")))) {
            System.out.println("Pojazd już wypożyczony.");
            return;
        }

        rentalRepository.rentVehicle(v.getId(), this.getId());
        v.addAttribute("rented", true);
        vehicleRepository.save();
        System.out.println("Wypożyczyłeś pojazd: " + v);
    }

    public void returnVehicle(int vehicleId) {
        Vehicle v = vehicleRepository.getVehicleById(vehicleId);
        if (v == null) {
            System.out.println("Pojazd nie istnieje.");
            return;
        }

        if (!"true".equals(String.valueOf(v.getAttribute("rented")))) {
            System.out.println("Ten pojazd nie był wypożyczony.");
            return;
        }

        rentalRepository.returnVehicle(v.getId(), this.getId());
        v.addAttribute("rented", false);
        vehicleRepository.save();
        System.out.println("Zwróciłeś pojazd: " + v);
    }

    public void listAvailableVehicles() {
        System.out.println("Dostępne pojazdy:");
        for (Vehicle v : vehicleRepository.getVehicles()) {
            if (!"true".equals(String.valueOf(v.getAttribute("rented")))) {
                System.out.println(v);
            }
        }
    }
}
