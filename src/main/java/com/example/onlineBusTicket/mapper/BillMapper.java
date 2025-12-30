package com.example.onlineBusTicket.mapper;


import com.example.onlineBusTicket.dto.bill.BillResponse;
import com.example.onlineBusTicket.entity.BillEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillMapper {

    BillResponse toResponse(BillEntity entity);
}
