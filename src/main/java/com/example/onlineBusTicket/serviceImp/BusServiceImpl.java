package com.example.onlineBusTicket.serviceImp;

import com.example.onlineBusTicket.exception.BadRequestException;
import com.example.onlineBusTicket.exception.NotFoundException;
import com.example.onlineBusTicket.dto.bus.BusCreateRequest;
import com.example.onlineBusTicket.dto.bus.BusResponse;
import com.example.onlineBusTicket.entity.BusEntity;
import com.example.onlineBusTicket.mapper.BusMapper;
import com.example.onlineBusTicket.repository.BusRepository;
import com.example.onlineBusTicket.service.BusService;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;

    public BusServiceImpl(BusRepository busRepository, BusMapper busMapper) {
        this.busRepository = busRepository;
        this.busMapper = busMapper;
    }

    @Override
    public BusResponse create(BusCreateRequest req) {
        busRepository.findByNumber(req.getNumber()).ifPresent(b -> {
            throw new BadRequestException("Bus number already exists");
        });

        BusEntity saved = busRepository.save(busMapper.toEntity(req));
        return busMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusResponse> list() {
        return busRepository.findAll().stream().map(busMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BusResponse getById(Long id) {
        BusEntity entity = busRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bus not found"));
        return busMapper.toResponse(entity);
    }
}

