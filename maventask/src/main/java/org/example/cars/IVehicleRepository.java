package org.example.cars;

import java.util.List;

public interface IVehicleRepository {
    void rentVehicle(int vehicleId);
    void returnVehicle(int vehicleId);
    List<Vehicle> getVehicles();
    void save(); // zmiany do pliku
    void load(); // pojazdy z pliku

    void addVehicle(Vehicle vehicle);

    boolean removeVehicle(int vehicleId);
}