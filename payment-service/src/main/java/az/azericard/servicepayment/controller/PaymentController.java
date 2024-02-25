package az.azericard.servicepayment.controller;

import az.azericard.core.domain.OperationResponse;
import az.azericard.servicepayment.domain.dto.PaymentDto;
import az.azericard.servicepayment.domain.dto.PaymentResponse;
import az.azericard.servicepayment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PaymentResponse>> getAllPayments(@PathVariable String username) {
        List<PaymentResponse> payments = paymentService.getAllUserPayments(username);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<OperationResponse> createPayment(@RequestBody PaymentDto paymentDto) {
        OperationResponse operationResponse = paymentService.createPayment(paymentDto);
        return new ResponseEntity<>(operationResponse, HttpStatus.OK);
    }
}
