package com.example.onlineBusTicket.service;

import com.example.onlineBusTicket.dto.reservation.ReservationResponse;

public interface ReservationQueryService {

    ReservationResponse getReservationByIdResilient(Long id);
}
