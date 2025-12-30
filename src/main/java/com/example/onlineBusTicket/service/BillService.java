package com.example.onlineBusTicket.service;

import com.example.onlineBusTicket.dto.bill.BillResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BillService {

    List<BillResponse>listSorted(Sort sort);
    BillResponse getByReservationId(Long reservationId);
}
