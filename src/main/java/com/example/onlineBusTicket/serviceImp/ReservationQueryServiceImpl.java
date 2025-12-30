package com.example.onlineBusTicket.serviceImp;


import com.example.onlineBusTicket.exception.NotFoundException;
import com.example.onlineBusTicket.external.ExternalReservationClient;
import com.example.onlineBusTicket.dto.reservation.ReservationResponse;
import com.example.onlineBusTicket.entity.ReservationEntity;
import com.example.onlineBusTicket.mapper.ReservationMapper;
import com.example.onlineBusTicket.repository.ReservationRepository;
import com.example.onlineBusTicket.service.PricingService;
import com.example.onlineBusTicket.service.ReservationQueryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional(readOnly = true)
public class ReservationQueryServiceImpl implements ReservationQueryService {

    private final ExternalReservationClient externalReservationClient;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final PricingService pricingService;

    public ReservationQueryServiceImpl(
            ExternalReservationClient externalReservationClient,
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper,
            PricingService pricingService
    ) {
        this.externalReservationClient = externalReservationClient;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.pricingService = pricingService;
    }

    @Override
    @Cacheable(cacheNames = "reservations", key = "#id")
    public ReservationResponse getReservationByIdResilient(Long id) {
        // 1) On tente la source externe (qui peut throw 504)
        try {
            externalReservationClient.fetchReservation(id);
            // Dans notre simulation, le client externe ne renvoie pas forcément les données.
            // On considère que si l'appel passe, on lit en DB (copie locale).
        } catch (ExternalReservationClient.GatewayTimeout504Exception ex) {
            // 2) Fallback DB (copie locale) si 504
            return loadFromDbAsResponse(id);
        }

        // 3) Chemin nominal : DB
        return loadFromDbAsResponse(id);
    }

    private ReservationResponse loadFromDbAsResponse(Long id) {
        ReservationEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        ReservationResponse resp = reservationMapper.toResponse(entity);
        resp.setTotalAmount(pricingService.computeTotal(entity));
        return resp;
    }
}
