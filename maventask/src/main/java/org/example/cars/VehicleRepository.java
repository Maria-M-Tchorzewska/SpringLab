package org.example.cars;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VehicleRepository implements IVehicleRepository {
    private static final String FILE_NAME = "vehicles.csv";
    private static final List<Vehicle> vehicles = new ArrayList<>();

    public VehicleRepository() {
        load();
    }

    public static Vehicle getVehicleById(int vehicleId) {
        return vehicles.stream()
                .filter(v -> v.getId() == vehicleId)
                .findFirst()
                .orElse(null);
    }


    @Override
    public void rentVehicle(int vehicleId) {
        for (Vehicle v : vehicles) {
            if (v.getId() == vehicleId && !v.isRented()) {
                v.setRented(true); //zmiana mozliwosci
                save();
                return;
            }
        }
        System.out.println("Pojazd o ID " + vehicleId + " nie istnieje lub jest już wynajęty.");
    }

    @Override
    public void returnVehicle(int vehicleId) {
        for (Vehicle v : vehicles) {
            if (v.getId() == vehicleId && v.isRented()) {
                v.setRented(false); //zmiana mozliwosci
                save();
                return;
            }
        }
        System.out.println("Pojazd o ID " + vehicleId + " nie istnieje lub nie był wynajęty.");
    }

    @Override
    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(vehicles); //zabezpieczenie przed modyfikacją
    }

    @Override
    public void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Vehicle v : vehicles) {
                if (v instanceof Car) {
                    writer.println(v.getId() + ";" + v.brand + ";" + v.model + ";" + v.year + ";" + v.price + ";" + v.isRented() + ";Car");
                } else if (v instanceof Motorcycle) {
                    writer.println(v.getId() + ";" + v.brand + ";" + v.model + ";" + v.year + ";" + v.price + ";" + v.isRented() + ";Motorcycle;" + ((Motorcycle) v).getCategory());
                }
            }
            System.out.println("Dane zapisane do pliku.");
        } catch (IOException e) {
            System.out.println("Błąd zapisu pliku: " + e.getMessage());
        }
    }

    @Override
    public void load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Brak pliku, generowanie domyślnych pojazdów...");
            generateDefaultVehicles();
            return;
        }

        vehicles.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 7) {
                    int id = Integer.parseInt(data[0]);
                    String brand = data[1];
                    String model = data[2];
                    int year = Integer.parseInt(data[3]);
                    double price = Double.parseDouble(data[4]);
                    boolean rented = Boolean.parseBoolean(data[5]);
                    String type = data[6];

                    Vehicle vehicle;
                    if (type.equals("Car")) {
                        vehicle = new Car(brand, model, year, price);
                    } else if (type.equals("Motorcycle")) {
                        String category = data[7];
                        vehicle = new Motorcycle(brand, model, year, price, category);
                    } else {
                        continue;
                    }
                    vehicle.setRented(rented);
                    vehicles.add(vehicle);
                }
            }
            System.out.println("Dane wczytane z pliku.");
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku: " + e.getMessage());
        }
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        save();
    }

    private void generateDefaultVehicles() {
        vehicles.add(new Car("Toyota", "Corolla", 2022, 25000));
        vehicles.add(new Car("Honda", "Civic", 2021, 23000));
        vehicles.add(new Motorcycle("Yamaha", "R1", 2022, 20000, "Sport"));
        vehicles.add(new Motorcycle("Harley-Davidson", "Street 750", 2021, 15000, "Cruiser"));
        save();
    }

    @Override
    public boolean removeVehicle(int vehicleId) {
        Vehicle vehicleToRemove = vehicles.stream()
                .filter(v -> v.getId() == vehicleId)
                .findFirst()
                .orElse(null);

        if (vehicleToRemove != null) {
            vehicles.remove(vehicleToRemove);
            save();
            return true;
        }
        return false;
    }

}
