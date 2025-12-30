package com.example.onlineBusTicket.controller;


import com.example.onlineBusTicket.dto.client.ClientCreateRequest;
import com.example.onlineBusTicket.dto.client.ClientResponse;
import com.example.onlineBusTicket.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {


    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientResponse> list() {
        return clientService.list();
    }

    @PostMapping
    public ClientResponse create(@Valid @RequestBody ClientCreateRequest req) {
        return clientService.create(req);
    }

    @GetMapping("/{id}")
    public ClientResponse get(@PathVariable Long id) {
        return clientService.getById(id);
    }
}

