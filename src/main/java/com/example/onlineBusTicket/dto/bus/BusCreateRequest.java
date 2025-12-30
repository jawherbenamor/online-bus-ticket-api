package com.example.onlineBusTicket.dto.bus;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalTime;

public class BusCreateRequest {

    @NotBlank
    @Size(max =40)
    private String number;

    @NotNull
    @Min(1)
    @Max(1000)
    private Integer seats;

    @NotNull
    private LocalTime departureTime;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal tripPrice;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public BigDecimal getTripPrice() {
        return tripPrice;
    }

    public void setTripPrice(BigDecimal tripPrice) {
        this.tripPrice = tripPrice;
    }
}
