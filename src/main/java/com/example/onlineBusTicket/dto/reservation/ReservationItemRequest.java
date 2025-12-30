package com.example.onlineBusTicket.dto.reservation;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationItemRequest {

    @NotNull
    private Long busId;

    @NotNull
    private LocalDate tripDate;

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {

        this.busId = busId;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }
}
