package az.azericard.serviceuser.controller;

import az.azericard.core.domain.OperationResponse;
import az.azericard.serviceuser.domain.dto.CardDto;
import az.azericard.serviceuser.domain.dto.CreateCardRequest;
import az.azericard.serviceuser.domain.dto.PaymentResponse;
import az.azericard.serviceuser.domain.entity.User;
import az.azericard.serviceuser.security.SecurityUtils;
import az.azericard.serviceuser.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        SecurityUtils.checkUserHasPermission(username);

        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/create-card")
    public ResponseEntity<OperationResponse> createCard(@RequestBody CreateCardRequest createCardRequest) {
        OperationResponse response = userService.createCardFor(createCardRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{username}/cards")
    public ResponseEntity<List<CardDto>> getAllCards(@PathVariable String username) {
        List<CardDto> response = userService.getAllCards(username);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{username}/payments")
    public ResponseEntity<List<PaymentResponse>> getAllPayments(@PathVariable String username) {
        List<PaymentResponse> payments = userService.getAllPayments(username);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

}
