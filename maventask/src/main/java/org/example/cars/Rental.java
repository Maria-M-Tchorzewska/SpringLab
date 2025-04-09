package org.example.cars;

import java.time.LocalDateTime;
import java.util.Objects;

public class Rental {
    private String id;
    private String vehicleId;
    private String userId;
    private String rentDate;     // było LocalDateTime
    private String returnDate;   // było LocalDateTime

    public Rental(String id, String vehicleId, String userId, String rentDate, String returnDate) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

    public String getId() { return id; }
    public String getVehicleId() { return vehicleId; }
    public String getUserId() { return userId; }
    public String getRentDate() { return rentDate; }
    public String getReturnDate() { return returnDate; }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id='" + id + '\'' +
                ", vehicleId='" + vehicleId + '\'' +
                ", userId='" + userId + '\'' +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rental)) return false;
        Rental rental = (Rental) o;
        return Objects.equals(id, rental.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
