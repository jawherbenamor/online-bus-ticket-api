package com.example.onlineBusTicket.dto.reservation;

import com.example.onlineBusTicket.reservationStatus.PaymentType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class PayReservationRequest {

    @NotNull
    private PaymentType paymentType;

    @Valid
    private PayPalDetails paypal;

    @Valid
    private CreditCardDetails creditCard;

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PayPalDetails getPaypal() {
        return paypal;
    }

    public void setPaypal(PayPalDetails paypal) {
        this.paypal = paypal;
    }

    public CreditCardDetails getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardDetails creditCard) {
        this.creditCard = creditCard;
    }
}
