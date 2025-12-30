package com.example.onlineBusTicket.controller;


import com.example.onlineBusTicket.dto.bus.BusCreateRequest;
import com.example.onlineBusTicket.dto.bus.BusResponse;
import com.example.onlineBusTicket.service.BusService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buses")
public class BusController {

    private final BusService busService;

    public BusController (BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    public List<BusResponse> list() {
        return busService.list();
    }

    @PostMapping
    public BusResponse create(@Valid @RequestBody BusCreateRequest req) {
        return busService.create(req);
    }

    @GetMapping("/{id}")
    public BusResponse get(@PathVariable Long id) {
        return busService.getById(id);
    }
}


