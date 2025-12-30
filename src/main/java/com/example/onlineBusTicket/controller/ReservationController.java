package com.example.onlineBusTicket.controller;

import com.example.onlineBusTicket.dto.bill.BillResponse;
import com.example.onlineBusTicket.dto.reservation.PayReservationRequest;
import com.example.onlineBusTicket.dto.reservation.ReservationCreateRequest;
import com.example.onlineBusTicket.dto.reservation.ReservationResponse;
import com.example.onlineBusTicket.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationResponse> list() {
        return reservationService.list();
    }

    @PostMapping
    public ReservationResponse create(@Valid @RequestBody ReservationCreateRequest req) {
        return reservationService.create(req);
    }

    @GetMapping("/{id}")
    public ReservationResponse get(@PathVariable Long id) {
        return reservationService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.delete(id);
    }

    @PostMapping("/{id}/pay")
    public BillResponse pay(@PathVariable Long id, @Valid @RequestBody PayReservationRequest req) {
        return reservationService.pay(id, req);
    }
}
