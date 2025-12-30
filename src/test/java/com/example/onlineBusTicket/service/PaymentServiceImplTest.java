package com.example.onlineBusTicket.service;

import com.example.onlineBusTicket.exception.BadRequestException;
import com.example.onlineBusTicket.dto.reservation.CreditCardDetails;
import com.example.onlineBusTicket.dto.reservation.PayPalDetails;
import com.example.onlineBusTicket.dto.reservation.PayReservationRequest;
import com.example.onlineBusTicket.reservationStatus.PaymentType;
import com.example.onlineBusTicket.serviceImp.PaymentServiceImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class PaymentServiceImplTest {

    private final PaymentServiceImpl paymentService = new PaymentServiceImpl();

    @Test
    void should_pay_with_paypal_when_email_provided() {
        PayReservationRequest req = new PayReservationRequest();
        req.setPaymentType(PaymentType.PAYPAL);

        PayPalDetails pp = new PayPalDetails();
        pp.setEmail("payer@example.com");
        req.setPaypal(pp);

        assertThat(paymentService.pay(1L, req)).isTrue();
    }

    @Test
    void should_fail_paypal_when_email_missing() {
        PayReservationRequest req = new PayReservationRequest();
        req.setPaymentType(PaymentType.PAYPAL);

        assertThatThrownBy(() -> paymentService.pay(1L, req))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_pay_with_credit_card_when_details_provided() {
        PayReservationRequest req = new PayReservationRequest();
        req.setPaymentType(PaymentType.CREDIT_CARD);

        CreditCardDetails cc = new CreditCardDetails();
        cc.setCardNumber("4111111111111111");
        cc.setExpiry("12/30");
        req.setCreditCard(cc);

        assertThat(paymentService.pay(1L, req)).isTrue();
    }
}

