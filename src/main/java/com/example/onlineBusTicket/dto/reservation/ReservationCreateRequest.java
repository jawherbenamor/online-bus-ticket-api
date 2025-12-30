package com.example.onlineBusTicket.dto.reservation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ReservationCreateRequest {

    @NotNull
    private Long clientId;

    @Valid
    @NotEmpty
    private List<ReservationItemRequest> items;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<ReservationItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ReservationItemRequest> items) {
        this.items = items;
    }
}
