package com.example.onlineBusTicket.service;

import com.example.onlineBusTicket.dto.client.ClientCreateRequest;
import com.example.onlineBusTicket.dto.client.ClientResponse;

import java.util.List;

public interface ClientService {
    ClientResponse create(ClientCreateRequest req);
    List<ClientResponse> list();
    ClientResponse getById(Long id);
}
