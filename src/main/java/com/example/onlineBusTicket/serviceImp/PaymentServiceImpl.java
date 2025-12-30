package com.example.onlineBusTicket.serviceImp;


import com.example.onlineBusTicket.exception.BadRequestException;
import com.example.onlineBusTicket.dto.reservation.PayReservationRequest;
import com.example.onlineBusTicket.reservationStatus.PaymentType;
import com.example.onlineBusTicket.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean pay(Long reservationId, PayReservationRequest req) {
        if (reservationId == null) {
            throw new BadRequestException("ReservationId is required");
        }
        if (req == null || req.getPaymentType() == null) {
            throw new BadRequestException("Payment type is required");
        }

        if (req.getPaymentType() == PaymentType.PAYPAL) {
            if (req.getPaypal() == null || req.getPaypal().getEmail() == null || req.getPaypal().getEmail().isBlank()) {
                throw new BadRequestException("PayPal email is required");
            }
            return true;

        } else if (req.getPaymentType() == PaymentType.CREDIT_CARD) {
            if (req.getCreditCard() == null) {
                throw new BadRequestException("Credit card details are required");
            }
            if (req.getCreditCard().getCardNumber() == null || req.getCreditCard().getCardNumber().isBlank()) {
                throw new BadRequestException("Card number is required");
            }
            if (req.getCreditCard().getExpiry() == null || req.getCreditCard().getExpiry().isBlank()) {
                throw new BadRequestException("Card expiry is required");
            }
            return true;
        }

        throw new BadRequestException("Unsupported payment type");
    }
}

