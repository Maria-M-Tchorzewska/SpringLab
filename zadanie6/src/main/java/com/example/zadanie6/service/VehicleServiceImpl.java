package com.example.zadanie6.service;

import com.example.zadanie6.model.Vehicle;
import com.example.zadanie6.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> findAllActive() {
        return vehicleRepository.findByActiveTrue();
    }

    public Optional<Vehicle> findById(String id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> findAvailableVehicles() {
        return vehicleRepository.findByRentedFalseAndActiveTrue();
    }

    public List<Vehicle> findRentedVehicles() {
        return vehicleRepository.findByRentedTrue();
    }

    public boolean isAvailable(String vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .map(v -> v.isActive() && !v.isRented())
                .orElse(false);
    }

    public void deleteById(String id) {
        vehicleRepository.findById(id).ifPresent(v -> {
            v.setActive(false); // soft delete
            vehicleRepository.save(v);
        });
    }
}

