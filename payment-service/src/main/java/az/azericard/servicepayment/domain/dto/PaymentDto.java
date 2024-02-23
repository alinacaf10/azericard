package az.azericard.servicepayment.domain.dto;

import az.azericard.servicepayment.domain.entity.Payment;
import az.azericard.servicepayment.domain.enumeration.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto {
    @NotBlank(message = "{field.notBlank}")
    private LocalDateTime timestamp;

    @NotBlank(message = "{field.notBlank}")
    private Integer productId;

    @NotBlank(message = "{field.notBlank}")
    private Integer userId;

    @NotBlank(message = "{field.notBlank}")
    private Integer cardId;

    @NotBlank(message = "{field.notBlank}")
    private PaymentStatus paymentStatus;

    public PaymentDto() {
    }

    public PaymentDto(Payment payment) {
        setTimestamp(payment.getTimestamp());
        setCardId(payment.getCardId());
        setProductId(payment.getProductId());
        setUserId(payment.getUserId());
        setPaymentStatus(payment.getPaymentStatus());
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
