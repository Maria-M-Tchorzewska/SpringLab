package com.example.zadanie6.repository;

import com.example.zadanie6.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findByActiveTrue();
    List<Vehicle> findByRentedTrue();
    List<Vehicle> findByRentedFalseAndActiveTrue();
}
