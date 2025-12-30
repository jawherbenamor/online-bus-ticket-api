package com.example.onlineBusTicket.mapper;


import com.example.onlineBusTicket.dto.reservation.ReservationItemResponse;
import com.example.onlineBusTicket.dto.reservation.ReservationResponse;
import com.example.onlineBusTicket.entity.ReservationEntity;
import com.example.onlineBusTicket.entity.ReservationItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "clientId", source="client.id")
    @Mapping(target = "totalAmount", ignore=true)
    ReservationResponse toResponse(ReservationEntity entity);

    @Mapping(target = "busId", source="bus.id")
    @Mapping(target = "busNumber", source = "bus.number")
    ReservationItemResponse toItemResponse(ReservationItemEntity entity);
}
