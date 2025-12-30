package com.example.onlineBusTicket.mapper;


import com.example.onlineBusTicket.dto.bus.BusCreateRequest;
import com.example.onlineBusTicket.dto.bus.BusResponse;
import com.example.onlineBusTicket.entity.BusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusMapper {

    BusEntity toEntity(BusCreateRequest req);
    BusResponse toResponse(BusEntity entity);
}
