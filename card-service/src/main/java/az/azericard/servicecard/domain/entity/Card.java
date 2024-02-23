package az.azericard.servicecard.domain.entity;

import az.azericard.servicecard.domain.enumeration.CardType;
import az.azericard.servicecard.domain.enumeration.CardValidityStatus;
import az.azericard.servicecard.domain.enumeration.Issuer;
import az.azericard.servicecard.domain.enumeration.PaymentProcessingNetwork;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String cardNumber;

    private LocalDate expirationDate;

    private char[] cvv;

    private BigDecimal balance;

    private CardType cardType;

    private Issuer issuer;

    private CardValidityStatus cardValidityStatus;

    private String cardHolder;

    private PaymentProcessingNetwork paymentProcessingNetwork;

    private LocalDateTime createdAt;

    public Card() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public char[] getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv.toCharArray();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public CardValidityStatus getCardValidityStatus() {
        return cardValidityStatus;
    }

    public void setCardValidityStatus(CardValidityStatus cardValidityStatus) {
        this.cardValidityStatus = cardValidityStatus;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public PaymentProcessingNetwork getPaymentProcessingNetwork() {
        return paymentProcessingNetwork;
    }

    public void setPaymentProcessingNetwork(PaymentProcessingNetwork paymentProcessingNetwork) {
        this.paymentProcessingNetwork = paymentProcessingNetwork;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
