package com.example.onlineBusTicket.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "buses", uniqueConstraints = {
        @UniqueConstraint(name = "uk_bus_number", columnNames = "number")
})
public class BusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Integer seats;

    @Column(nullable = false)
    private LocalTime departureTime;

    @Column(nullable = false)
    private BigDecimal tripPrice;

    public BusEntity() {
    }

    public BusEntity(Long id, String number, Integer seats, LocalTime departureTime, BigDecimal tripPrice) {
        this.id = id;
        this.number = number;
        this.seats = seats;
        this.departureTime = departureTime;
        this.tripPrice = tripPrice;
    }

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