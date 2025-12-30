package com.example.onlineBusTicket.serviceImp;

import com.example.onlineBusTicket.exception.NotFoundException;
import com.example.onlineBusTicket.dto.bill.BillResponse;
import com.example.onlineBusTicket.entity.BillEntity;
import com.example.onlineBusTicket.mapper.BillMapper;
import com.example.onlineBusTicket.repository.BillRepository;
import com.example.onlineBusTicket.service.BillService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BillServiceImpl implements BillService {


    private final BillRepository billRepository;
    private final BillMapper billMapper;

    public BillServiceImpl(BillRepository billRepository, BillMapper billMapper) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillResponse> listSorted(Sort sort) {
        return billRepository.findAll(sort).stream().map(billMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BillResponse getByReservationId(Long reservationId) {
        BillEntity entity = billRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new NotFoundException("Bill not found for reservationId=" + reservationId));
        return billMapper.toResponse(entity);
    }
}

