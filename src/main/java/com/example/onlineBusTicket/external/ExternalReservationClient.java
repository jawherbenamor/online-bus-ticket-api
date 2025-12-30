package com.example.onlineBusTicket.external;

import com.example.onlineBusTicket.dto.reservation.ReservationResponse;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ExternalReservationClient {

    private final Random random = new Random();

    public ReservationResponse fetchReservation(Long id) {
        int r = random.nextInt(10);
        if (r < 2) {
            throw new GatewayTimeout504Exception("External API timeout");
        }
        return null;
    }

    public static class GatewayTimeout504Exception extends RuntimeException {
        public GatewayTimeout504Exception(String msg) { super(msg); }
    }
}

