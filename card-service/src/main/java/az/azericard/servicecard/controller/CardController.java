package az.azericard.servicecard.controller;

import az.azericard.servicecard.domain.dto.CreateCardRequest;
import az.azericard.servicecard.domain.dto.OperationResponse;
import az.azericard.servicecard.domain.entity.Card;
import az.azericard.servicecard.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping()
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<OperationResponse> createCard(@RequestBody CreateCardRequest cardRequest) {
        boolean isCardCreated = cardService.createCard(cardRequest);
        if (isCardCreated) {
            var successOperationResponse = new OperationResponse(true, "Card is created!");
            return new ResponseEntity<>(successOperationResponse, HttpStatus.CREATED);
        }

        var failOperationResponse = new OperationResponse(false, "Failed to create a card!");
        return new ResponseEntity<>(failOperationResponse, HttpStatus.EXPECTATION_FAILED);
    }
}
