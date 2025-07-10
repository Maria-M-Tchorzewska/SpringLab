package com.umcsuser.carrent.repositories.impl.jdbc;

import java.sql.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umcsuser.carrent.db.JdbcConnectionManager;
import com.umcsuser.carrent.models.Vehicle;
import com.umcsuser.carrent.repositories.VehicleRepository;

public class VehicleJdbcRepository implements VehicleRepository {

    private final Gson gson = new Gson();

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        try (Connection connection = JdbcConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String attrJson = rs.getString("attributes");
                Map<String, Object> attributes = gson.fromJson(attrJson, new TypeToken<Map<String, Object>>(){}.getType());

                Vehicle vehicle = Vehicle.builder()
                        .id(rs.getInt("id"))
                        .category(rs.getString("category"))
                        .brand(rs.getString("brand"))
                        .model(rs.getString("model"))
                        .year(rs.getInt("year"))
                        .plate(rs.getString("plate"))
                        .price(rs.getDouble("price"))
                        .attributes(attributes != null ? attributes : new HashMap<>())
                        .build();
                list.add(vehicle);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while reading vehicles", e);
        }
        return list;
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";
        try (Connection connection = JdbcConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String attrJson = rs.getString("attributes");
                    Map<String, Object> attributes = gson.fromJson(attrJson, new TypeToken<Map<String, Object>>(){}.getType());

                    Vehicle vehicle = Vehicle.builder()
                            .id(rs.getInt("id"))
                            .category(rs.getString("category"))
                            .brand(rs.getString("brand"))
                            .model(rs.getString("model"))
                            .year(rs.getInt("year"))
                            .plate(rs.getString("plate"))
                            .price(rs.getDouble("price"))
                            .attributes(attributes != null ? attributes : new HashMap<>())
                            .build();
                    return Optional.of(vehicle);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while reading vehicle", e);
        }
        return Optional.empty();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        try (Connection connection = JdbcConnectionManager.getInstance().getConnection()) {

            if (vehicle.getId() != null) {
                String updateSql = "UPDATE vehicles SET category = ?, brand = ?, model = ?, year = ?, plate = ?, price = ?, attributes = ?::jsonb WHERE id = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setString(1, vehicle.getCategory());
                    updateStmt.setString(2, vehicle.getBrand());
                    updateStmt.setString(3, vehicle.getModel());
                    updateStmt.setInt(4, vehicle.getYear());
                    updateStmt.setString(5, vehicle.getPlate());
                    updateStmt.setDouble(6, vehicle.getPrice());
                    updateStmt.setString(7, gson.toJson(vehicle.getAttributes()));
                    updateStmt.setInt(8, vehicle.getId());
                    updateStmt.executeUpdate();
                }
            } else {
                String insertSql = "INSERT INTO vehicles (category, brand, model, year, plate, price, attributes) VALUES (?, ?, ?, ?, ?, ?, ?::jsonb)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    insertStmt.setString(1, vehicle.getCategory());
                    insertStmt.setString(2, vehicle.getBrand());
                    insertStmt.setString(3, vehicle.getModel());
                    insertStmt.setInt(4, vehicle.getYear());
                    insertStmt.setString(5, vehicle.getPlate());
                    insertStmt.setDouble(6, vehicle.getPrice());
                    insertStmt.setString(7, gson.toJson(vehicle.getAttributes()));
                    insertStmt.executeUpdate();

                    try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            vehicle.setId(generatedKeys.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred", e);
        }

        return vehicle;
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM vehicles WHERE id = ?";
        try (Connection connection = JdbcConnectionManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(id));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred", e);
        }
    }
}
