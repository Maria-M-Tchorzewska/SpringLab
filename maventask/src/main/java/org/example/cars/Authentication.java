package org.example.cars;

public class Authentication {
    private final IUserRepository userRepository;

    public Authentication(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isAuthenticated(String login, String password) {
        User user = userRepository.getUser(login);
        if (user == null) {
            System.out.println("Użytkownik nie istnieje.");
            return false;
        }

        String hashedInput = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);

        if (user.getPassword().equals(hashedInput)) {
            System.out.println("Uwierzytelnienie zakończone sukcesem.");
            return true;
        } else {
            System.out.println("Nieprawidłowe hasło.");
            return false;
        }
    }
}
