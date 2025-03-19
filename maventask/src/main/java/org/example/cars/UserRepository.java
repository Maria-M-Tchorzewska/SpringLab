package org.example.cars;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private static final String FILE_NAME = "users.csv";
    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        load();
    }

    @Override
    public User getUser(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.println(user.getLogin() + ";" + user.getPassword() + ";" + user.getRole() + ";" +
                        (user.getRentedCar() != null ? user.getRentedCar().getId() : "brak"));
            }
            System.out.println("Użytkownicy zapisani do pliku.");
        } catch (IOException e) {
            System.out.println("Błąd zapisu użytkowników: " + e.getMessage());
        }
    }

    private void load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println(" Plik użytkowników nie istnieje. Tworzenie nowego.");
            generateDefaultUsers();
            return;
        }

        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 3) {
                    String login = data[0];
                    String password = data[1];  // juzzapisane
                    String role = data[2];
                    User user = role.equals("ADMIN") ? new Admin(login, password) : new User(login, password, role);

                    if (data.length == 4 && !data[3].equals("brak")) {
                        int rentedCarId = Integer.parseInt(data[3]);
                        Vehicle rentedCar = VehicleRepository.getVehicleById(rentedCarId);
                        if (rentedCar != null) {
                            user.rentCar(rentedCar);
                        }
                    }

                    users.add(user);
                }
            }
            System.out.println("Użytkownicy wczytani z pliku.");
        } catch (IOException e) {
            System.out.println("Błąd odczytu użytkowników: " + e.getMessage());
        }
    }

    private void generateDefaultUsers() {
        users.add(new Admin("admin", hashPassword("admin123")));
        users.add(new User("u1", hashPassword("123"), "USER"));
        users.add(new User("u2", hashPassword("1234"), "USER"));
        save();
    }

    private String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    public boolean login(String login, String password) {
        User user = getUser(login);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            System.out.println("Zalogowano pomyślnie");
            return true;
        }
        System.out.println("Błędny login lub hasło");
        return false;
    }
}
