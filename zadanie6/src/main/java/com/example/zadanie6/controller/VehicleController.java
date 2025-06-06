package com.example.zadanie6.controller;

import com.example.zadanie6.model.Vehicle;
import com.example.zadanie6.service.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vehicle> getAll() {
        return service.findAll();
    }

    @GetMapping("/active")
    public List<Vehicle> getActive() {
        return service.findAllActive();
    }

    @PostMapping
    public Vehicle save(@RequestBody Vehicle v) {
        return service.save(v);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }

    @GetMapping("/available")
    public List<Vehicle> getAvailable() {
        return service.findAvailableVehicles();
    }

    @GetMapping("/rented")
    public List<Vehicle> getRented() {
        return service.findRentedVehicles();
    }
}
