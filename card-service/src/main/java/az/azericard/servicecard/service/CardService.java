package az.azericard.servicecard.service;

import az.azericard.core.domain.OperationResponse;
import az.azericard.servicecard.domain.dto.CardDto;
import az.azericard.servicecard.domain.dto.CreateCardRequest;
import az.azericard.servicecard.domain.dto.PaymentDto;

import java.util.List;

public interface CardService {
    List<CardDto> getAllCards(String cardHolder);

    boolean createCard(CreateCardRequest cardRequest);

    OperationResponse makePayment(PaymentDto paymentDto);
}
