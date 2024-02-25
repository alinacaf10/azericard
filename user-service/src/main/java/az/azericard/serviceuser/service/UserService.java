package az.azericard.serviceuser.service;

import az.azericard.core.domain.OperationResponse;
import az.azericard.serviceuser.domain.dto.*;
import az.azericard.serviceuser.domain.entity.User;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);

    List<CardDto> getAllCards(String username);

    UserDto registerUser(RegisterDto registerDto);

    void checkLoginCredentials(LoginDto loginDto);

    OperationResponse createCardFor(CreateCardRequest createCardRequest);

    List<PaymentResponse> getAllPayments(String username);
}
