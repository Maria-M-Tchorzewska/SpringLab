package org.example.cars;

import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String login, String plainPassword) {
        User user = userRepository.getUser(login);
        if (user != null && BCrypt.checkpw(plainPassword, user.getPassword())) {
            System.out.println("Zalogowano pomyślnie jako: " + login);
            return user;
        }
        System.out.println("Błędny login lub hasło.");
        return null;
    }

    public boolean register(String login, String plainPassword, String role) {
        if (userRepository.getUser(login) != null) {
            System.out.println("Użytkownik o podanym loginie już istnieje.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        String userId = UUID.randomUUID().toString();
        User newUser = new User(userId, login, hashedPassword, role);
        userRepository.getUsers().add(newUser);
        userRepository.save();
        System.out.println("Zarejestrowano użytkownika: " + login);
        return true;
    }
}
