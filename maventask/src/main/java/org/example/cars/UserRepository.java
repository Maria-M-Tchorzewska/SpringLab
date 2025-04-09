package org.example.cars;

import com.google.gson.reflect.TypeToken;
import org.mindrot.jbcrypt.BCrypt;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository implements IUserRepository {
    private static final String FILE_NAME = "users.json";
    private final List<User> users = new ArrayList<>();
    private final JsonFileStorage<User> storage;

    public UserRepository() {
        Type listType = new TypeToken<List<User>>() {}.getType();
        this.storage = new JsonFileStorage<>(FILE_NAME, listType);
        load();
        if (users.isEmpty()) {
            generateDefaultUsers();
        }

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
        storage.save(users);
        System.out.println("Użytkownicy zapisani do JSON-a.");
    }

    private void load() {
        users.clear();
        users.addAll(storage.load());
        System.out.println("Użytkownicy wczytani z JSON-a.");
    }

    private void generateDefaultUsers() {
        users.add(new User(UUID.randomUUID().toString(), "admin", hashPassword("admin123"), "ADMIN"));
        users.add(new User(UUID.randomUUID().toString(), "user1", hashPassword("123"), "USER"));
        users.add(new User(UUID.randomUUID().toString(), "user2", hashPassword("1234"), "USER"));
        save();
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean login(String login, String plainPassword) {
        User user = getUser(login);
        if (user != null && BCrypt.checkpw(plainPassword, user.getPassword())) {
            System.out.println("Zalogowano pomyślnie.");
            return true;
        }
        System.out.println("Błędny login lub hasło.");
        return false;
    }
}
