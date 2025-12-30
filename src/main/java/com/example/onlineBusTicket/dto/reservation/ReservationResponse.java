package com.example.onlineBusTicket.dto.reservation;

import com.example.onlineBusTicket.reservationStatus.ReservationStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ReservationResponse {

    private Long id;
    private Long clientId;
    private ReservationStatus status;
    private Instant createdAt;
    private List<ReservationItemResponse> items;
    private BigDecimal totalAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<ReservationItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ReservationItemResponse> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
