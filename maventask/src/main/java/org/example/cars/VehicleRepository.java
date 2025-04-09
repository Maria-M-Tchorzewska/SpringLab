package org.example.cars;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

public class VehicleRepository implements IVehicleRepository {
    private static final String FILE_NAME = "vehicles.json";
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final JsonFileStorage<Vehicle> storage;
    private final RentalRepository rentalRepository;

    public VehicleRepository() {
        Type listType = new TypeToken<List<Vehicle>>() {}.getType();
        this.storage = new JsonFileStorage<>(FILE_NAME, listType);
        this.rentalRepository = new RentalRepository();
        load();
    }

    public Vehicle getVehicleById(int vehicleId) {
        return vehicles.stream()
                .filter(v -> {
                    try {
                        return Integer.parseInt(v.getId()) == vehicleId;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .findFirst()
                .orElse(null);
    }

    @Override
    public void rentVehicle(int vehicleId) {
        Vehicle v = getVehicleById(vehicleId);
        if (v == null) {
            System.out.println("Nie znaleziono pojazdu o ID: " + vehicleId);
            return;
        }

        if ("true".equals(String.valueOf(v.getAttribute("rented")))) {
            System.out.println("Pojazd już wypożyczony.");
            return;
        }

        String userId = "debug-user";

        v.addAttribute("rented", true);
        rentalRepository.rentVehicle(v.getId(), userId);
        save();
    }

    @Override
    public void returnVehicle(int vehicleId) {
        Vehicle v = getVehicleById(vehicleId);
        if (v == null) {
            System.out.println("Nie znaleziono pojazdu o ID: " + vehicleId);
            return;
        }

        if (!"true".equals(String.valueOf(v.getAttribute("rented")))) {
            System.out.println("Pojazd nie jest wypożyczony.");
            return;
        }

        String userId = "debug-user";
        rentalRepository.returnVehicle(v.getId(), userId);
        v.addAttribute("rented", false);
        save();
    }

    @Override
    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    @Override
    public void save() {
        storage.save(vehicles);
        System.out.println("Zapisano pojazdy do JSON-a.");
    }

    @Override
    public void load() {
        vehicles.clear();
        vehicles.addAll(storage.load());
        System.out.println("Wczytano pojazdy z JSON-a.");
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        if (vehicle.getAttribute("rented") == null) {
            vehicle.addAttribute("rented", false);
        }
        vehicles.add(vehicle);
        save();
    }

    @Override
    public boolean removeVehicle(int vehicleId) {
        Vehicle v = getVehicleById(vehicleId);
        if (v != null) {
            vehicles.remove(v);
            save();
            return true;
        }
        return false;
    }
}
