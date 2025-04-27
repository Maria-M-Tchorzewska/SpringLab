package com.umcsuser.carrent.app;

import com.umcsuser.carrent.models.User;
import com.umcsuser.carrent.models.Vehicle;
import com.umcsuser.carrent.services.AuthService;
import com.umcsuser.carrent.services.RentalService;
import com.umcsuser.carrent.services.VehicleService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {

    private final AuthService authService;
    private final VehicleService vehicleService;
    private final RentalService rentalService;
    private final Scanner scanner = new Scanner(System.in);
    private User currentUser;

    public App(AuthService authService, VehicleService vehicleService, RentalService rentalService) {
        this.authService = authService;
        this.vehicleService = vehicleService;
        this.rentalService = rentalService;
    }

    public void run() {
        System.out.println("--- System Wypożyczalni Pojazdów ---");

        boolean running = true;
        while (running) {
            System.out.println("\n1. Logowanie");
            System.out.println("2. Rejestracja");
            System.out.println("0. Wyjście");
            System.out.print("Wybierz opcję: ");

            int option = readInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> login();
                case 2 -> register();
                case 0 -> {
                    System.out.println("Zamykanie aplikacji...");
                    running = false;
                }
                default -> System.out.println("Nieprawidłowa opcja!");
            }
        }
    }

    private void login() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Hasło: ");
        String password = scanner.nextLine();

        Optional<User> userOptional = authService.login(login, password);
        if (userOptional.isPresent()) {
            currentUser = userOptional.get();
            System.out.println("Zalogowano jako: " + currentUser.getLogin());
            userMenu();
        } else {
            System.out.println("Błędny login lub hasło.");
        }
    }



    private void register() {
        System.out.print("Nowy login: ");
        String login = scanner.nextLine();
        System.out.print("Nowe hasło: ");
        String password = scanner.nextLine();
        System.out.print("Rola (USER lub ADMIN): ");
        String role = scanner.nextLine().toUpperCase();

        boolean success = authService.register(login, password, role);
        if (success) {
            System.out.println("Rejestracja zakończona. Możesz się teraz zalogować.");
        } else {
            System.out.println("Rejestracja nie powiodła się.");
        }
    }

    private void userMenu() {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Lista dostępnych pojazdów");
            System.out.println("2. Wypożycz pojazd");
            System.out.println("3. Zwróć pojazd");
            if ("ADMIN".equals(currentUser.getRole())) {
                System.out.println("4. Dodaj pojazd");
                System.out.println("5. Usuń pojazd");
            }
            System.out.println("0. Wyloguj");

            System.out.print("Wybierz opcję: ");
            int choice = readInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> listAvailableVehicles();
                case 2 -> rentVehicle();
                case 3 -> returnVehicle();
                case 4 -> {
                    if ("ADMIN".equals(currentUser.getRole())) addVehicle();
                }
                case 5 -> {
                    if ("ADMIN".equals(currentUser.getRole())) removeVehicle();
                }
                case 0 -> {
                    System.out.println("Wylogowano.");
                    currentUser = null;
                    loggedIn = false;
                }
                default -> System.out.println("Nieprawidłowa opcja!");
            }
        }
    }

    private void listAvailableVehicles() {
        List<Vehicle> vehicles = vehicleService.listAvailableVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("Brak dostępnych pojazdów.");
        } else {
            vehicles.forEach(System.out::println);
        }
    }

    private void rentVehicle() {
        System.out.print("Podaj ID pojazdu do wypożyczenia: ");
        String vehicleId = scanner.nextLine();
        rentalService.rentVehicle(currentUser.getId(), vehicleId);
        System.out.println("Wypożyczono pojazd.");
    }

    private void returnVehicle() {
        System.out.print("Podaj ID pojazdu do zwrotu: ");
        String vehicleId = scanner.nextLine();
        rentalService.returnVehicle(currentUser.getId(), vehicleId);
        System.out.println("Pojazd zwrócony.");
    }

    private void addVehicle() {
        System.out.print("Kategoria: ");
        String category = scanner.nextLine();
        System.out.print("Marka: ");
        String brand = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Rok: ");
        int year = readInt();
        System.out.print("Tablica rejestracyjna: ");
        String plate = scanner.nextLine();
        System.out.print("Cena: ");
        double price = readDouble();

        Vehicle newVehicle = new Vehicle(null, category, brand, model, year, plate, price, new HashMap<>());
        vehicleService.addVehicle(newVehicle);
        System.out.println("Dodano nowy pojazd.");
    }

    private void removeVehicle() {
        System.out.print("Podaj ID pojazdu do usunięcia: ");
        String id = scanner.nextLine();
        vehicleService.removeVehicle(id);
        System.out.println("Pojazd usunięty.");
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Podaj liczbę!");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private double readDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Podaj liczbę zmiennoprzecinkową!");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
