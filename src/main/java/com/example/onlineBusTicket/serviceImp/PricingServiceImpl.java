package com.example.onlineBusTicket.serviceImp;


import com.example.onlineBusTicket.entity.ReservationEntity;
import com.example.onlineBusTicket.entity.ReservationItemEntity;
import com.example.onlineBusTicket.service.PricingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PricingServiceImpl implements PricingService {


    private static final BigDecimal DISCOUNT_THRESHOLD = new BigDecimal("100.00");
    private static final BigDecimal DISCOUNT_FACTOR = new BigDecimal("0.95");

    @Override
    public BigDecimal computeTotal(ReservationEntity reservation) {
        if (reservation == null || reservation.getItems() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (ReservationItemEntity item : reservation.getItems()) {
            BigDecimal price = item.getBus().getTripPrice();
            if (price == null) price = BigDecimal.ZERO;

            if (price.compareTo(DISCOUNT_THRESHOLD) > 0) {
                price = price.multiply(DISCOUNT_FACTOR);
            }

            total = total.add(price);
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}

