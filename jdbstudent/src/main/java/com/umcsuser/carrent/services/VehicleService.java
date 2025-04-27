package com.umcsuser.carrent.services;


import com.umcsuser.carrent.models.Rental;
import com.umcsuser.carrent.models.Vehicle;
import com.umcsuser.carrent.repositories.RentalRepository;
import com.umcsuser.carrent.repositories.VehicleRepository;

import java.util.List;

public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;

    public VehicleService(VehicleRepository vehicleRepository, RentalRepository rentalRepository) {
        this.vehicleRepository = vehicleRepository;
        this.rentalRepository = rentalRepository;
    }

    public List<Vehicle> listAvailableVehicles() {
        List<String> rentedVehicleIds = rentalRepository.findAll().stream()
                .filter(r -> r.getReturnDate() == null)
                .map(Rental::getVehicleId)
                .toList();

        return vehicleRepository.findAll().stream()
                .filter(v -> !rentedVehicleIds.contains(v.getId()))
                .toList();
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void removeVehicle(String id) {
        vehicleRepository.deleteById(id);
    }
}

