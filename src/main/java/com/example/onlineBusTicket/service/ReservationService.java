package com.example.onlineBusTicket.service;

import com.example.onlineBusTicket.dto.bill.BillResponse;
import com.example.onlineBusTicket.dto.reservation.PayReservationRequest;
import com.example.onlineBusTicket.dto.reservation.ReservationCreateRequest;
import com.example.onlineBusTicket.dto.reservation.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse create(ReservationCreateRequest req);
    List<ReservationResponse> list();
    ReservationResponse getById(Long id);
    void delete(Long id);
    BillResponse pay(Long reservationId, PayReservationRequest req);
}
