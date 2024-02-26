package az.azericard.serviceuser.domain.dto;

import java.io.Serializable;

public class CreateCardRequest implements Serializable {
    private String cardType;
    private String cardHolder;

    public CreateCardRequest() {
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType.toUpperCase();
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
}
