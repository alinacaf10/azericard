package az.azericard.serviceuser.service.impl;

import az.azericard.core.domain.OperationResponse;
import az.azericard.core.exception.ResourceAlreadyExistException;
import az.azericard.serviceuser.domain.dto.*;
import az.azericard.serviceuser.domain.entity.User;
import az.azericard.serviceuser.repository.UserRepository;
import az.azericard.serviceuser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DefaultUserService implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultUserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WebClient.Builder webClientBuilder;

    public DefaultUserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            WebClient.Builder webClientBuilder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        Objects.requireNonNull(username, "username must not be null!");

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found!"));
    }

    @Override
    public List<CardDto> getAllCards(String username) {
        Objects.requireNonNull(username, "username can't be null!");

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found!"));


        Mono<List<CardDto>> listMono = webClientBuilder.build().get()
                .uri("http://card-service/api/v1/cards",
                        uriBuilder -> uriBuilder.queryParam("cardHolder", user.getFullName()).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });

        return listMono.block();
    }

    @Override
    @Transactional
    public UserDto registerUser(final RegisterDto registerDto) {
        Objects.requireNonNull(registerDto, "registerDto can not be null!");

        checkUsernameExist(registerDto.getUsername());

        var user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUsername(registerDto.getUsername().toLowerCase());

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        LOG.debug("User registered {} on {}", user, LocalDateTime.now());

        return new UserDto(user);
    }


    @Override
    @Transactional(readOnly = true)
    public void checkLoginCredentials(LoginDto loginDto) {
        LOG.debug("validating credentials for {}", loginDto.username());
        var user = this.userRepository.findUserByUsername(loginDto.username())
                .orElseThrow(() ->
                        new BadCredentialsException("User with '%s' not found!".formatted(loginDto.username())));

        if (!this.passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new BadCredentialsException("Username or password is invalid!");
        }
    }

    @Override
    public OperationResponse createCardFor(CreateCardRequest createCardRequest) {
        Objects.requireNonNull(createCardRequest, "createCardRequest can't be null!");

        User user = userRepository.findUserByUsername(createCardRequest.getCardHolder())
                .orElseThrow(() -> new UsernameNotFoundException("User '" + createCardRequest.getCardHolder() + "' not found!"));

        createCardRequest.setCardHolder(user.getFullName());

        Mono<OperationResponse> operationResponseMono = webClientBuilder.build().post()
                .uri("http://card-service/api/v1/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createCardRequest)
                .retrieve()
                .bodyToMono(OperationResponse.class);

        return operationResponseMono.block();
    }

    @Override
    public List<PaymentResponse> getAllPayments(String username) {
        Objects.requireNonNull(username, "username can't be null or empty!");

        userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found!"));

        List<PaymentResponse> payments = webClientBuilder.build().get()
                .uri("http://payment-service/api/v1/payments/{username}", username)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PaymentResponse>>() {
                })
                .block();

        return payments;
    }

    /**
     * Checks whether the username exists or not. If username
     * exists, then {@link ResourceAlreadyExistException} will be thrown, otherwise
     * nothing will be happened.
     *
     * @param username a username of {@link User}
     */
    private void checkUsernameExist(String username) {
        userRepository.findUserByUsername(username)
                .ifPresent(u -> {
                    throw new ResourceAlreadyExistException("Username '%s' is already exist!".formatted(username));
                });
    }
}
