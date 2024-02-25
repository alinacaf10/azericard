package az.azericard.servicecard.service.impl;

import az.azericard.core.domain.OperationResponse;
import az.azericard.servicecard.domain.dto.CardDto;
import az.azericard.servicecard.domain.dto.CreateCardRequest;
import az.azericard.servicecard.domain.dto.PaymentDto;
import az.azericard.servicecard.domain.entity.Card;
import az.azericard.servicecard.domain.enumeration.CardType;
import az.azericard.servicecard.domain.enumeration.CardValidityStatus;
import az.azericard.servicecard.domain.enumeration.Issuer;
import az.azericard.servicecard.domain.enumeration.PaymentProcessingNetwork;
import az.azericard.servicecard.repository.CardRepository;
import az.azericard.servicecard.service.CardService;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.encrypt.TextEncryptor;
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
    private final TextEncryptor textEncryptor;

    public DefaultCardService(CardRepository cardRepository, TextEncryptor textEncryptor) {
        this.cardRepository = cardRepository;
        this.textEncryptor = textEncryptor;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardDto> getAllCards(String cardHolder) {
        Objects.requireNonNull(cardHolder, "cardHolder can't be null!");

        List<Card> cards = cardRepository.findAllByCardHolder(cardHolder);

        return cards.stream()
                .map(card -> {
                    var cardDto = new CardDto();
                    cardDto.setCardType(card.getCardType());
                    cardDto.setCreatedAt(card.getCreatedAt());
                    cardDto.setCardValidityStatus(card.getCardValidityStatus());
                    cardDto.setBalance(card.getBalance());
                    cardDto.setExpirationDate(card.getExpirationDate());
                    cardDto.setIssuer(card.getIssuer());
                    cardDto.setPaymentProcessingNetwork(card.getPaymentProcessingNetwork());
                    cardDto.setCardHolder(card.getCardHolder());

                    var decryptedCvv = textEncryptor.decrypt(String.valueOf(card.getCvv()));
                    cardDto.setCvv(decryptedCvv);

                    var decryptedCardNumber = textEncryptor.decrypt(card.getCardNumber());
                    cardDto.setCardNumber(decryptedCardNumber);

                    return cardDto;
                })
                .toList();
    }

    @Override
    @Transactional
    public boolean createCard(CreateCardRequest cardRequest) {
        Objects.requireNonNull(cardRequest, "cardRequest must not be null!");

        var card = new Card();
        card.setCardType(cardRequest.getCardType());
        card.setCardHolder(cardRequest.getCardHolder());

        card.setIssuer(cardRequest.getIssuer() == null ? Issuer.BANK : cardRequest.getIssuer());

        var paymentProcessingNetwork = cardRequest.getPaymentProcessingNetwork() == null ?
                PaymentProcessingNetwork.MASTERCARD :
                cardRequest.getPaymentProcessingNetwork();
        card.setPaymentProcessingNetwork(paymentProcessingNetwork);

        String cardNumber = generateUniqueCardNumber();
        String encryptedCardNumber = textEncryptor.encrypt(cardNumber);
        card.setCardNumber(encryptedCardNumber);

        var expiredInYears = cardRequest.getCardType() == CardType.CREDIT_CARD ? 4 : 2;
        card.setExpirationDate(LocalDate.now().plusYears(expiredInYears));

        String cvv = RandomStringUtils.randomNumeric(3);
        String encodedCvv = textEncryptor.encrypt(cvv);
        card.setCvv(encodedCvv);

        card.setCardValidityStatus(CardValidityStatus.ACTIVE);
        card.setCreatedAt(LocalDateTime.now());
        card.setBalance(BigDecimal.ZERO);

        cardRepository.save(card);
        return true;
    }

    @Override
    @Transactional
    public OperationResponse makePayment(PaymentDto paymentDto) {
        Objects.requireNonNull(paymentDto, "paymentDto can't be null!");

        String encryptedCardNumber = textEncryptor.encrypt(paymentDto.getCardNumber());

        Optional<Card> optionalCard = cardRepository.findCardByCardNumber(encryptedCardNumber);
        if (optionalCard.isEmpty()) {
            return new OperationResponse(false, "Invalid card credentials!");
        }

        Card card = optionalCard.get();

        if (card.getBalance().compareTo(paymentDto.getAmount()) < 0) {
            return new OperationResponse(false,
                    "There is not enough balance to pay " + paymentDto.getAmount() + " amount!");
        }

        BigDecimal balanceAfterPayment = card.getBalance().subtract(paymentDto.getAmount());
        card.setBalance(balanceAfterPayment);
        cardRepository.save(card);

        return new OperationResponse(true, "Payment is successful!");
    }

    @Transactional(readOnly = true)
    public @Nullable String generateUniqueCardNumber() {
        boolean isUnique = false;
        String cardNumber = null;
        while (!isUnique) {
            cardNumber = RandomStringUtils.randomNumeric(16);
            Optional<Card> cardByCardNumber = cardRepository.findCardByCardNumber(textEncryptor.encrypt(cardNumber));
            if (cardByCardNumber.isEmpty())
                isUnique = true;
        }

        return cardNumber;
    }
}
