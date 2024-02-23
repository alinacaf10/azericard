package az.azericard.servicecard.service.impl;

import az.azericard.servicecard.domain.dto.CreateCardRequest;
import az.azericard.servicecard.domain.entity.Card;
import az.azericard.servicecard.domain.enumeration.CardType;
import az.azericard.servicecard.domain.enumeration.CardValidityStatus;
import az.azericard.servicecard.repository.CardRepository;
import az.azericard.servicecard.service.CardService;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultCardService implements CardService {
    private final CardRepository cardRepository;

    public DefaultCardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    @Transactional
    public boolean createCard(CreateCardRequest cardRequest) {
        Objects.requireNonNull(cardRequest, "cardRequest must not be null!");

        var card = new Card();
        card.setCardType(cardRequest.getCardType());
        card.setCardHolder(cardRequest.getCardHolder());
        card.setIssuer(cardRequest.getIssuer());
        card.setPaymentProcessingNetwork(cardRequest.getPaymentProcessingNetwork());

        String cardNumber = generateUniqueCardNumber();
        card.setCardNumber(cardNumber);
        card.setBalance(BigDecimal.ZERO);

        var expiredInYears = cardRequest.getCardType() == CardType.CREDIT_CARD ? 4 : 2;
        card.setExpirationDate(LocalDate.now().plusYears(expiredInYears));

        card.setCvv(RandomStringUtils.randomNumeric(3));

        card.setCardValidityStatus(CardValidityStatus.ACTIVE);
        card.setCreatedAt(LocalDateTime.now());
        return true;
    }

    @Transactional(readOnly = true)
    public @Nullable String generateUniqueCardNumber() {
        boolean isUnique = false;
        String cardNumber = null;
        while (!isUnique) {
            cardNumber = RandomStringUtils.randomNumeric(16);
            Optional<Card> cardByCardNumber = cardRepository.findCardByCardNumber(cardNumber);
            if (cardByCardNumber.isEmpty())
                isUnique = true;
        }

        return cardNumber;
    }
}
