package az.azericard.servicepayment.service;

import az.azericard.core.domain.OperationResponse;
import az.azericard.servicepayment.domain.dto.PaymentDto;
import az.azericard.servicepayment.domain.dto.PaymentResponse;
import az.azericard.servicepayment.domain.entity.Payment;

import java.util.List;

public interface PaymentService {
    List<PaymentResponse> getAllUserPayments(String username);

    OperationResponse createPayment(PaymentDto paymentDto);
}
