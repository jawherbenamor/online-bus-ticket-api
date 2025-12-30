package com.example.onlineBusTicket.mapper;

import com.example.onlineBusTicket.dto.client.ClientCreateRequest;
import com.example.onlineBusTicket.dto.client.ClientResponse;
import com.example.onlineBusTicket.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientEntity toEntity(ClientCreateRequest req);
    ClientResponse toResponse(ClientEntity entity);
}
