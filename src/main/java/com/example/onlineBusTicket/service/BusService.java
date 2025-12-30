package com.example.onlineBusTicket.service;

import com.example.onlineBusTicket.dto.bus.BusCreateRequest;
import com.example.onlineBusTicket.dto.bus.BusResponse;

import java.util.List;

public interface BusService {

    BusResponse create(BusCreateRequest req);
    List<BusResponse> list();
    BusResponse getById(Long id);
}
