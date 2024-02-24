package az.azericard.servicepayment.service.impl;

import az.azericard.servicepayment.domain.entity.Payment;
import az.azericard.servicepayment.repository.PaymentRepository;
import az.azericard.servicepayment.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class DefaultPaymentService implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final WebClient webClient;

    public DefaultPaymentService(PaymentRepository paymentRepository, WebClient webClient) {
        this.paymentRepository = paymentRepository;
        this.webClient = webClient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public void paymentOrder() {
        Boolean result = webClient.get()
                .uri("http://localhost:8002/api/v1/products")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        if (result){
            //save
        }
    }
}
