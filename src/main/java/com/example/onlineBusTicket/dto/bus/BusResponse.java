package com.example.onlineBusTicket.dto.bus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class BusResponse {

    private Long id;
    private String number;
    private Integer seats;
    private LocalTime departureTime;
    private BigDecimal tripPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
