package com.example.zadanie6.repository;

import com.example.zadanie6.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    Optional<Rental> findByVehicleIdAndActiveTrue(String vehicleId);
}

