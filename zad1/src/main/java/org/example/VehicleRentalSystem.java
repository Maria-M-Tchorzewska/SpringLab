package org.example;

import java.util.List;
import java.util.Scanner;

public class VehicleRentalSystem {
    private static final IVehicleRepository repository = new VehicleRepository();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Wypożyczalnia Pojazdów ---");
            System.out.println("1. Wyświetl dostępne pojazdy");
            System.out.println("2. Wypożycz pojazd");
            System.out.println("3. Zwróć pojazd");
            System.out.println("4. Dodaj nowy pojazd");
            System.out.println("5. Wyjście");
            System.out.print("Wybierz opcję: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    List<Vehicle> vehicles = repository.getVehicles();
                    for (Vehicle v : vehicles) {
                        System.out.println(v);
                    }
                    break;
                case 2:
                    System.out.print("Podaj ID pojazdu do wypożyczenia: ");
                    int rentId = scanner.nextInt();
                    repository.rentVehicle(rentId);
                    break;
                case 3:
                    System.out.print("Podaj ID pojazdu do zwrotu: ");
                    int returnId = scanner.nextInt();
                    repository.returnVehicle(returnId);
                    break;
                case 4:
                    addNewVehicle(scanner);
                    break;
                case 5:
                    System.out.println("Zamykanie systemu...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Niepoprawny wybór!");
            }
        }
    }

    private static void addNewVehicle(Scanner scanner) {
        System.out.print("Podaj markę: ");
        String brand = scanner.next();
        System.out.print("Podaj model: ");
        String model = scanner.next();
        System.out.print("Podaj rok: ");
        int year = scanner.nextInt();
        System.out.print("Podaj cenę: ");
        double price = scanner.nextDouble();
        System.out.print("Podaj typ pojazdu: ");
        String type = scanner.next();

        if (type.equalsIgnoreCase("Car")) {
            repository.addVehicle(new Car(brand, model, year, price));
        } else if (type.equalsIgnoreCase("Motorcycle")) {
            System.out.print("Podaj kategorię motocykla: ");
            String category = scanner.next();
            repository.addVehicle(new Motorcycle(brand, model, year, price, category));
        } else {
            System.out.println("Niepoprawny typ pojazdu!");
        }
    }
}
