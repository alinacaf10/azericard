package az.azericard.servicecard.service;

import az.azericard.servicecard.domain.dto.CreateCardRequest;
import az.azericard.servicecard.domain.entity.Card;

import java.util.List;

public interface CardService {
    List<Card> getAllCards();

    boolean createCard(CreateCardRequest cardRequest);
}
