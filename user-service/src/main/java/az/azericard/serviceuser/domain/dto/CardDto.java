package az.azericard.serviceuser.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDto {
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvv;
    private BigDecimal balance;
    private String cardType;
    private String cardValidityStatus;
    private String cardHolder;
    private String paymentProcessingNetwork;
    private LocalDateTime createdAt;

    public CardDto() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardValidityStatus() {
        return cardValidityStatus;
    }

    public void setCardValidityStatus(String cardValidityStatus) {
        this.cardValidityStatus = cardValidityStatus;
    }

    public String getPaymentProcessingNetwork() {
        return paymentProcessingNetwork;
    }

    public void setPaymentProcessingNetwork(String paymentProcessingNetwork) {
        this.paymentProcessingNetwork = paymentProcessingNetwork;
    }
}
