package com.example.zadanie6.controller;

import com.example.zadanie6.model.Rental;
import com.example.zadanie6.service.RentalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService service;

    public RentalController(RentalService service) {
        this.service = service;
    }

    @PostMapping("/rent")
    public Rental rent(@RequestParam String vehicleId, @RequestParam String userId) {
        return service.rent(vehicleId, userId);
    }

    @PostMapping("/return")
    public boolean returnRental(@RequestParam String vehicleId, @RequestParam String userId) {
        return service.returnRental(vehicleId, userId);
    }

    @GetMapping
    public List<Rental> all() {
        return service.findAll();
    }
}
