package org.example.cars;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;

public class RentalRepository {
    private static final String FILE_NAME = "rentals.json";
    private final JsonFileStorage<Rental> storage;
    private final List<Rental> rentals;

    public RentalRepository() {
        Type listType = new TypeToken<List<Rental>>() {}.getType();
        this.storage = new JsonFileStorage<>(FILE_NAME, listType);
        this.rentals = new ArrayList<>(storage.load());
    }

    public void rentVehicle(String vehicleId, String userId) {
        String date = java.time.LocalDateTime.now().toString();
        Rental rental = new Rental(UUID.randomUUID().toString(), vehicleId, userId, date, null);
        rentals.add(rental);
        save();
        System.out.println("Wypożyczono pojazd " + vehicleId + " przez użytkownika " + userId);
    }

    public void returnVehicle(String vehicleId, String userId) {
        Rental active = getActiveRental(vehicleId, userId);
        if (active != null) {
            String date = java.time.LocalDateTime.now().toString();
            active.setReturnDate(date);
            save();
            System.out.println("Zwrócono pojazd " + vehicleId + " przez użytkownika " + userId);
        } else {
            System.out.println("Nie znaleziono aktywnego wypożyczenia dla pojazdu " + vehicleId + " i użytkownika " + userId);
        }
    }

    public Rental getActiveRental(String vehicleId, String userId) {
        return rentals.stream()
                .filter(r -> r.getVehicleId().equals(vehicleId)
                        && r.getUserId().equals(userId)
                        && r.getReturnDate() == null)
                .findFirst()
                .orElse(null);
    }

    public List<Rental> getAllRentals() {
        return Collections.unmodifiableList(rentals);
    }

    private void save() {
        storage.save(rentals);
    }
}
