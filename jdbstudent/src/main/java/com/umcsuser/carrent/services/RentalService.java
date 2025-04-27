package com.umcsuser.carrent.services;

import com.umcsuser.carrent.models.Rental;
import com.umcsuser.carrent.repositories.RentalRepository;

import java.util.UUID;

public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void rentVehicle(String userId, String vehicleId) {
        Rental rental = new Rental(
                UUID.randomUUID().toString(),
                vehicleId,
                userId,
                java.time.LocalDate.now().toString(),
                null
        );
        rentalRepository.save(rental);
    }


    public void returnVehicle(String userId, String vehicleId) {
        Rental rental = rentalRepository.findByVehicleIdAndReturnDateIsNull(vehicleId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono aktywnego wypożyczenia"));

        if (!rental.getUserId().equals(userId)) {
            throw new RuntimeException("Nie masz prawa zwracać tego pojazdu!");
        }

        rental.setReturnDate(java.time.LocalDate.now().toString());
        rentalRepository.save(rental);
    }



}

