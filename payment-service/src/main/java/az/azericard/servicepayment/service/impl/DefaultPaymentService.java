package az.azericard.servicepayment.service.impl;

import az.azericard.core.domain.OperationResponse;
import az.azericard.servicepayment.domain.dto.CardPaymentDto;
import az.azericard.servicepayment.domain.dto.PaymentDto;
import az.azericard.servicepayment.domain.dto.PaymentResponse;
import az.azericard.servicepayment.domain.dto.ProductDto;
import az.azericard.servicepayment.domain.entity.Payment;
import az.azericard.servicepayment.domain.enumeration.PaymentStatus;
import az.azericard.servicepayment.repository.PaymentRepository;
import az.azericard.servicepayment.service.PaymentService;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public class DefaultPaymentService implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final TextEncryptor textEncryptor;
    private final WebClient.Builder webClientBuilder;

    public DefaultPaymentService(
            PaymentRepository paymentRepository,
            TextEncryptor textEncryptor,
            WebClient.Builder webClientBuilder
    ) {
        this.paymentRepository = paymentRepository;
        this.textEncryptor = textEncryptor;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllUserPayments(String username) {
        Objects.requireNonNull(username, "username can't be null!");
        return paymentRepository.findAllByUsername(username)
                .stream()
                .map(payment -> {
                    var response = new PaymentResponse();
                    response.setTimestamp(payment.getTimestamp());
                    response.setPaymentStatus(payment.getPaymentStatus());
                    response.setProductName(payment.getProductName());
                    response.setProductPrice(payment.getProductPrice());
                    response.setUsername(payment.getUsername());
                    return response;
                })
                .toList();
    }

    @Override
    @Transactional
    public OperationResponse createPayment(PaymentDto paymentDto) {
        var payment = new Payment();
        payment.setProductName(paymentDto.getProductName());
        payment.setUsername(paymentDto.getUsername());
        payment.setCardNumber(textEncryptor.encrypt(paymentDto.getCardNumber()));

        ProductDto product = getProduct(paymentDto);
        if (product == null) {
            var message = "Product doesn't exist!";
            saveFailedPayment(message, payment);
            return new OperationResponse(false, message);
        }

        if (product.getQuantityInStock() < 1) {
            var message = "Stock is empty for " + product.getName();
            saveFailedPayment(message, payment);
            return new OperationResponse(false, "Stock is empty for " + product.getName());
        }

        OperationResponse response = makeCardPayment(paymentDto, product);

        if (!response.isSuccess()) {
            String message = response.message();
            saveFailedPayment(message, payment);
            new OperationResponse(false, message);
        }

        payment.setProductPrice(product.getPrice());
        payment.setPaymentStatus(PaymentStatus.SUCCESSFUL);
        paymentRepository.save(payment);

        return new OperationResponse(true, "Payment is successful!");
    }

    private void saveFailedPayment(String message, Payment payment) {
        payment.setPaymentStatus(PaymentStatus.FAILED);
        payment.setMessage(message);
        paymentRepository.save(payment);
    }

    private OperationResponse makeCardPayment(PaymentDto paymentDto, ProductDto product) {
        return webClientBuilder.build().post()
                .uri("http://card-service/api/v1/cards/make-payment")
                .bodyValue(new CardPaymentDto(paymentDto.getCardNumber(), product.getPrice()))
                .retrieve()
                .bodyToMono(OperationResponse.class)
                .block();
    }

    private ProductDto getProduct(PaymentDto paymentDto) {
        return webClientBuilder.build().get()
                .uri("http://product-service/api/v1/products",
                        uriBuilder -> uriBuilder.queryParam("product", paymentDto.getProductName()).build())
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }

    private void decreaseProductCountInStock(ProductDto productDto) {
        webClientBuilder.build().put()
                .uri("http://product-service/api/v1/products/{product}/decrement-stock", productDto.getName())
                .retrieve()
                .bodyToMono(OperationResponse.class);
    }
}
