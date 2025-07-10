package com.umcsuser.carrent.app;

import com.umcsuser.carrent.db.JdbcConnectionManager;
import com.umcsuser.carrent.repositories.RentalRepository;
import com.umcsuser.carrent.repositories.UserRepository;
import com.umcsuser.carrent.repositories.VehicleRepository;
import com.umcsuser.carrent.repositories.impl.jdbc.RentalJdbcRepository;
import com.umcsuser.carrent.repositories.impl.jdbc.UserJdbcRepository;
import com.umcsuser.carrent.repositories.impl.jdbc.VehicleJdbcRepository;
import com.umcsuser.carrent.services.AuthService;
import com.umcsuser.carrent.services.RentalService;
import com.umcsuser.carrent.services.VehicleService;

public class Main {
    public static void main(String[] args) {

        System.out.println("Start aplikacji w trybie JDBC");
        System.out.println("Łączenie z bazą danych pod adresem:");
        System.out.println(System.getenv("DB_URL"));

        UserRepository userRepo = new UserJdbcRepository();
        VehicleRepository vehicleRepo = new VehicleJdbcRepository();
        RentalRepository rentalRepo = new RentalJdbcRepository();

        AuthService authService = new AuthService(userRepo);
        VehicleService vehicleService = new VehicleService(vehicleRepo, rentalRepo);
        RentalService rentalService = new RentalService(rentalRepo);

        App app = new App(authService, vehicleService, rentalService);
        app.run();
    }
}
