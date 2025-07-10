package com.umcsuser.carrent.repositories.impl.json;

import com.google.gson.reflect.TypeToken;
import com.umcsuser.carrent.db.JsonFileStorage;
import com.umcsuser.carrent.models.Vehicle;
import com.umcsuser.carrent.repositories.VehicleRepository;

import java.util.*;

public class VehicleJsonRepository implements VehicleRepository {

    private final JsonFileStorage<Vehicle> storage =
            new JsonFileStorage<>("vehicles.json", new TypeToken<List<Vehicle>>(){}.getType());

    private final List<Vehicle> vehicles;

    public VehicleJsonRepository() {
        this.vehicles = new ArrayList<>(storage.load());
    }

    @Override
    public List<Vehicle> findAll() {
        return new ArrayList<>(vehicles);
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        try {
            int vehicleId = Integer.parseInt(id);
            return vehicles.stream()
                    .filter(v -> Objects.equals(v.getId(), vehicleId))
                    .findFirst();
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            int maxId = vehicles.stream()
                    .map(Vehicle::getId)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder())
                    .orElse(0);
            vehicle.setId(maxId + 1);
        } else {
            deleteById(String.valueOf(vehicle.getId()));
        }
        vehicles.add(vehicle);
        storage.save(vehicles);
        return vehicle;
    }

    @Override
    public void deleteById(String id) {
        try {
            int vehicleId = Integer.parseInt(id);
            vehicles.removeIf(v -> Objects.equals(v.getId(), vehicleId));
            storage.save(vehicles);
        } catch (NumberFormatException ignored) {
        }
    }
}
