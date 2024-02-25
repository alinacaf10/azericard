package az.azericard.servicecard.domain.dto;

import java.math.BigDecimal;

public class PaymentDto {
    private String cardNumber;
    private BigDecimal amount;

    public PaymentDto() {}

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
