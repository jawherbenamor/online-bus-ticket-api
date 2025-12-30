package com.example.onlineBusTicket.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation_items", uniqueConstraints = @UniqueConstraint(
        name = "uk_reservation_bus_date",
        columnNames = {"reservation_id", "bus_id", "trip_date"}
))
public class ReservationItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false, foreignKey = @ForeignKey(name = "fk_item_reservation"))
    private ReservationEntity reservation;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false, foreignKey = @ForeignKey(name = "fk_item_bus"))
    private BusEntity bus;

    @Column(name = "trip_date", nullable = false)
    private LocalDate tripDate;

    public ReservationItemEntity() {
    }

    public ReservationItemEntity(Long id, ReservationEntity reservation, BusEntity bus, LocalDate tripDate) {
        this.id = id;
        this.reservation = reservation;
        this.bus = bus;
        this.tripDate = tripDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationEntity getReservationEntity() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public BusEntity getBus() {
        return bus;
    }

    public void setBus(BusEntity bus) {
        this.bus = bus;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }
}
