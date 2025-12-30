package com.example.onlineBusTicket.service;

import com.example.onlineBusTicket.entity.BusEntity;
import com.example.onlineBusTicket.entity.ReservationEntity;
import com.example.onlineBusTicket.entity.ReservationItemEntity;
import com.example.onlineBusTicket.serviceImp.PricingServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PricingServiceImplTest {

    private final PricingServiceImpl pricingService = new PricingServiceImpl();

    @Test
    void should_apply_5_percent_discount_when_trip_price_greater_than_100() {
        BusEntity b1 = new BusEntity();
        b1.setTripPrice(new BigDecimal("120.00"));
        BusEntity b2 = new BusEntity();
        b2.setTripPrice(new BigDecimal("80.00"));

        ReservationItemEntity i1 = new ReservationItemEntity();
        i1.setBus(b1);
        i1.setTripDate(LocalDate.of(2025, 12, 30));

        ReservationItemEntity i2 = new ReservationItemEntity();
        i2.setBus(b2);
        i2.setTripDate(LocalDate.of(2025, 12, 31));

        ReservationEntity r = new ReservationEntity();
        r.setItems(List.of(i1, i2));

        BigDecimal total = pricingService.computeTotal(r);

        // 120 * 0.95 = 114.00 ; + 80 = 194.00
        assertThat(total).isEqualByComparingTo(new BigDecimal("194.00"));
    }

    @Test
    void should_not_apply_discount_when_trip_price_is_100_or_less() {
        BusEntity b1 = new BusEntity();
        b1.setTripPrice(new BigDecimal("100.00"));

        ReservationItemEntity i1 = new ReservationItemEntity();
        i1.setBus(b1);

        ReservationEntity r = new ReservationEntity();
        r.setItems(List.of(i1));

        BigDecimal total = pricingService.computeTotal(r);

        assertThat(total).isEqualByComparingTo(new BigDecimal("100.00"));
    }
}

