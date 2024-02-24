package az.azericard.servicepayment.service;

import az.azericard.servicepayment.domain.entity.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments();
    void paymentOrder();
}
