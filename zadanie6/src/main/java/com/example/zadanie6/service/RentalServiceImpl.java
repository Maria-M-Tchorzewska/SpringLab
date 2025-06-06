package com.example.zadanie6.service;

import com.example.zadanie6.model.Rental;
import com.example.zadanie6.model.Vehicle;
import com.example.zadanie6.repository.RentalRepository;
import com.example.zadanie6.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;

    public RentalServiceImpl(RentalRepository rentalRepository, VehicleRepository vehicleRepository) {
        this.rentalRepository = rentalRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public boolean isVehicleRented(String vehicleId) {
        return rentalRepository.findByVehicleIdAndActiveTrue(vehicleId).isPresent();
    }

    @Override
    public Optional<Rental> findActiveRentalByVehicleId(String vehicleId) {
        return rentalRepository.findByVehicleIdAndActiveTrue(vehicleId);
    }

    @Override
    public Rental rent(String vehicleId, String userId) {
        Vehicle vehicle = vehicleRepository.findById(String.valueOf(Long.parseLong(vehicleId)))
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (vehicle.isRented() || !vehicle.isActive()) {
            throw new RuntimeException("Vehicle not available");
        }

        vehicle.setRented(true);
        vehicleRepository.save(vehicle);

        Rental rental = new Rental();
        rental.setVehicleId(vehicleId);
        rental.setUserId(userId);
        rental.setStartDate(LocalDateTime.now());
        rental.setActive(true);
        rental.setEndDate(null);

        return rentalRepository.save(rental);
    }

    @Override
    public boolean returnRental(String vehicleId, String userId) {
        Optional<Rental> rentalOpt = rentalRepository.findByVehicleIdAndActiveTrue(vehicleId);

        if (rentalOpt.isPresent()) {
            Rental rental = rentalOpt.get();
            rental.setActive(false);
            rental.setEndDate(LocalDateTime.now());
            rentalRepository.save(rental);

            Vehicle vehicle = vehicleRepository.findById(String.valueOf(Long.parseLong(vehicleId)))
                    .orElseThrow(() -> new RuntimeException("Vehicle not found"));

            vehicle.setRented(false);
            vehicleRepository.save(vehicle);

            return true;
        }

        return false;
    }

    @Override
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }
}
