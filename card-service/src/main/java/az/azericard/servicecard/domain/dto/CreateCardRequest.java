package az.azericard.servicecard.domain.dto;

import az.azericard.servicecard.domain.enumeration.CardType;
import az.azericard.servicecard.domain.enumeration.Issuer;
import az.azericard.servicecard.domain.enumeration.PaymentProcessingNetwork;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class CreateCardRequest implements Serializable {
    @NotBlank(message = "{field.notBlank}")
    private CardType cardType;

    @NotBlank(message = "{field.notBlank}")
    private Issuer issuer;

    private String cardHolder;
    private PaymentProcessingNetwork paymentProcessingNetwork;

    public CreateCardRequest() {
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
}
