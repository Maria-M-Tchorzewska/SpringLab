package org.example.cars;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private final String FILE_PATH = "users.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public UserRepository() {
        load();
    }

    public User getUser(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    public List<User> getUsers() {
        return users;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Błąd zapisu użytkowników: " + e.getMessage());
        }
    }

    public void load() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> loadedUsers = gson.fromJson(reader, userListType);
            if (loadedUsers != null) {
                users.clear();
                users.addAll(loadedUsers);
            }
        } catch (IOException e) {
            System.out.println("Nie udało się załadować users");
            save();
        }
    }
}
