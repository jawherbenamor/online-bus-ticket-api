package com.example.onlineBusTicket.serviceImp;

import com.example.onlineBusTicket.exception.BadRequestException;
import com.example.onlineBusTicket.exception.NotFoundException;
import com.example.onlineBusTicket.dto.client.ClientCreateRequest;
import com.example.onlineBusTicket.dto.client.ClientResponse;
import com.example.onlineBusTicket.entity.ClientEntity;
import com.example.onlineBusTicket.mapper.ClientMapper;
import com.example.onlineBusTicket.repository.ClientRepository;
import com.example.onlineBusTicket.service.ClientService;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientResponse create(ClientCreateRequest req) {
        clientRepository.findByEmail(req.getEmail()).ifPresent(c -> {
            throw new BadRequestException("Email already exists");
        });
        ClientEntity saved = clientRepository.save(clientMapper.toEntity(req));
        return clientMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> list() {
        return clientRepository.findAll().stream().map(clientMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse getById(Long id) {
        ClientEntity entity = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));
        return clientMapper.toResponse(entity);
    }
}

