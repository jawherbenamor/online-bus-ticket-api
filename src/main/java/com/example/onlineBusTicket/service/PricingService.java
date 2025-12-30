package com.example.onlineBusTicket.service;

import com.example.onlineBusTicket.entity.ReservationEntity;

import java.math.BigDecimal;

public interface PricingService {

    BigDecimal computeTotal(ReservationEntity reservation);
}
