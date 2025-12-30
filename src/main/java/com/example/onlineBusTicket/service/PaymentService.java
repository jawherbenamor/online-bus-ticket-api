package com.example.onlineBusTicket.service;

import com.example.onlineBusTicket.dto.reservation.PayReservationRequest;

public interface PaymentService {

    boolean pay(Long reservationId, PayReservationRequest req);
}
