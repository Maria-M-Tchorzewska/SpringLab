package org.example.cars;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService2 {

    private final JdbcConnectionManager connectionManager;


    public AuthService2(JdbcConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public User login(String login, String password) {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";

        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Login failed!", e);
        }
    }
}
