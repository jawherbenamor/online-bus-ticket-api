package com.example.onlineBusTicket.serviceImp;


import com.example.onlineBusTicket.dto.bill.BillResponse;
import com.example.onlineBusTicket.dto.reservation.PayReservationRequest;
import com.example.onlineBusTicket.dto.reservation.ReservationCreateRequest;
import com.example.onlineBusTicket.dto.reservation.ReservationItemRequest;
import com.example.onlineBusTicket.dto.reservation.ReservationResponse;
import com.example.onlineBusTicket.exception.BadRequestException;
import com.example.onlineBusTicket.exception.NotFoundException;
import com.example.onlineBusTicket.entity.*;
import com.example.onlineBusTicket.mapper.BillMapper;
import com.example.onlineBusTicket.mapper.ReservationMapper;
import com.example.onlineBusTicket.repository.BillRepository;
import com.example.onlineBusTicket.repository.BusRepository;
import com.example.onlineBusTicket.repository.ClientRepository;
import com.example.onlineBusTicket.repository.ReservationRepository;
import com.example.onlineBusTicket.reservationStatus.ReservationStatus;
import com.example.onlineBusTicket.service.PaymentService;
import com.example.onlineBusTicket.service.PricingService;
import com.example.onlineBusTicket.service.ReservationService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ClientRepository clientRepository;
    private final BusRepository busRepository;
    private final ReservationRepository reservationRepository;
    private final BillRepository billRepository;

    private final ReservationMapper reservationMapper;
    private final BillMapper billMapper;

    private final PricingService pricingService;
    private final PaymentService paymentService;

    public ReservationServiceImpl(
            ClientRepository clientRepository,
            BusRepository busRepository,
            ReservationRepository reservationRepository,
            BillRepository billRepository,
            ReservationMapper reservationMapper,
            BillMapper billMapper,
            PricingService pricingService,
            PaymentService paymentService
    ) {
        this.clientRepository = clientRepository;
        this.busRepository = busRepository;
        this.reservationRepository = reservationRepository;
        this.billRepository = billRepository;
        this.reservationMapper = reservationMapper;
        this.billMapper = billMapper;
        this.pricingService = pricingService;
        this.paymentService = paymentService;
    }

    @Override
    public ReservationResponse create(ReservationCreateRequest req) {
        ClientEntity client = clientRepository.findById(req.getClientId())
                .orElseThrow(() -> new NotFoundException("Client not found"));

        ReservationEntity reservation = new ReservationEntity();
        reservation.setClient(client);
        reservation.setStatus(ReservationStatus.PENDING);

        // items
        for (ReservationItemRequest itemReq : req.getItems()) {
            BusEntity bus = busRepository.findById(itemReq.getBusId())
                    .orElseThrow(() -> new NotFoundException("Bus not found (id=" + itemReq.getBusId() + ")"));

            ReservationItemEntity item = new ReservationItemEntity();
            item.setReservation(reservation);
            item.setBus(bus);
            item.setTripDate(itemReq.getTripDate());

            reservation.getItems().add(item);
        }

        ReservationEntity saved = reservationRepository.save(reservation);

        ReservationResponse resp = reservationMapper.toResponse(saved);
        resp.setTotalAmount(pricingService.computeTotal(saved));
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> list() {
        return reservationRepository.findAll().stream().map(entity -> {
            ReservationResponse resp = reservationMapper.toResponse(entity);
            resp.setTotalAmount(pricingService.computeTotal(entity));
            return resp;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponse getById(Long id) {
        ReservationEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        ReservationResponse resp = reservationMapper.toResponse(entity);
        resp.setTotalAmount(pricingService.computeTotal(entity));
        return resp;
    }

    @Override
    public void delete(Long id) {
        ReservationEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        if (entity.getStatus() == ReservationStatus.PAID) {
            throw new BadRequestException("Cannot delete a PAID reservation");
        }

        reservationRepository.delete(entity);
    }

    @Override
    public BillResponse pay(Long reservationId, PayReservationRequest req) {
        ReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new BadRequestException("Cannot pay a CANCELLED reservation");
        }
        if (reservation.getStatus() == ReservationStatus.PAID) {
            throw new BadRequestException("Reservation already paid");
        }

        billRepository.findByReservationId(reservationId).ifPresent(b -> {
            throw new BadRequestException("Bill already exists for this reservation");
        });

        boolean paymentOk = paymentService.pay(reservationId, req);
        if (!paymentOk) {
            throw new BadRequestException("Payment failed");
        }

        BigDecimal total = pricingService.computeTotal(reservation);

        reservation.setStatus(ReservationStatus.PAID);
        reservationRepository.save(reservation);

        BillEntity bill = new BillEntity();
        bill.setReservationId(reservationId);
        bill.setPaymentType(req.getPaymentType());
        bill.setAmount(total);

        BillEntity savedBill = billRepository.save(bill);
        return billMapper.toResponse(savedBill);
    }
}

