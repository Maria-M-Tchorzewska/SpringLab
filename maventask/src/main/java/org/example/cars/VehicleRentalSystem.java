package org.example.cars;

import java.util.*;
import java.util.stream.Collectors;

public class VehicleRentalSystem {

    public static void main(String[] args) {
        VehicleRepository vehicleRepository = new VehicleRepository();
        UserRepository userRepository = new UserRepository();
        RentalRepository rentalRepository = new RentalRepository();
        AuthService authService = new AuthService(userRepository);
       Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Logowanie do systemu ---");
        System.out.print("Podaj login: ");
        String login = scanner.next();
        System.out.print("Podaj hasło: ");
        String password = scanner.next();

        User user = authService.login(login, password);
        if (user == null) {
            System.out.println("Zamykanie systemu...");
            return;
        }

        boolean isAdmin = "ADMIN".equals(user.getRole());

        while (true) {
            System.out.println("\n--- Wypożyczalnia Pojazdów ---");
            System.out.println("1. Wyświetl pojazdy");
            System.out.println("2. Wypożycz pojazd");
            System.out.println("3. Zwróć pojazd");
            if (isAdmin) System.out.println("4. Dodaj nowy pojazd");
            if (isAdmin) System.out.println("5. Usuń pojazd");
            System.out.println("0. Wyjście");
            System.out.print("Wybierz opcję: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    List<Vehicle> all = vehicleRepository.getVehicles();
                    List<Vehicle> visible = isAdmin ? all : all.stream()
                            .filter(v -> !"true".equals(String.valueOf(v.getAttributes().get("rented"))))
                            .collect(Collectors.toList());
                    visible.forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Podaj ID pojazdu do wypożyczenia: ");
                    int rentId = scanner.nextInt();
                    rentalRepository.rentVehicle(String.valueOf(rentId), user.getId());
                    Vehicle rentVehicle = vehicleRepository.getVehicleById(rentId);
                    if (rentVehicle != null) rentVehicle.getAttributes().put("rented", true);
                    vehicleRepository.save();
                    break;
                case 3:
                    System.out.print("Podaj ID pojazdu do zwrotu: ");
                    int returnId = scanner.nextInt();
                    rentalRepository.returnVehicle(String.valueOf(returnId), user.getId());
                    Vehicle returnVehicle = vehicleRepository.getVehicleById(returnId);
                    if (returnVehicle != null) returnVehicle.getAttributes().put("rented", false);
                    vehicleRepository.save();
                    break;
                case 4:
                    if (!isAdmin) break;
                    addNewVehicle(scanner, vehicleRepository);
                    break;
                case 5:
                    if (!isAdmin) break;
                    System.out.print("Podaj ID pojazdu do usunięcia: ");
                    int deleteId = scanner.nextInt();
                    vehicleRepository.removeVehicle(deleteId);
                    break;
                case 0:
                    System.out.println("Zamykanie systemu...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Niepoprawny wybór!");
            }
        }
    }

    private static void addNewVehicle(Scanner scanner, VehicleRepository vehicleRepository) {
        System.out.print("Podaj ID: ");
        String id = scanner.next();
        System.out.print("Podaj kategorię: ");
        String category = scanner.next();
        System.out.print("Podaj markę: ");
        String brand = scanner.next();
        System.out.print("Podaj model: ");
        String model = scanner.next();
        System.out.print("Podaj rok: ");
        int year = scanner.nextInt();
        System.out.print("Podaj tablicę rejestracyjną: ");
        String plate = scanner.next();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("rented", false);

        Vehicle vehicle = new Vehicle(id, category, brand, model, year, plate, attributes);
        vehicleRepository.addVehicle(vehicle);
    }

}