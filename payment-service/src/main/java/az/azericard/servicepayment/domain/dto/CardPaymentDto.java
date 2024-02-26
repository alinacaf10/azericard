package az.azericard.servicepayment.domain.dto;

import java.math.BigDecimal;

public class CardPaymentDto {
    private String cardNumber;
    private BigDecimal price;

    public CardPaymentDto() {
    }

    public CardPaymentDto(String cardNumber, BigDecimal price) {
        this.cardNumber = cardNumber;
        this.price = price;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
