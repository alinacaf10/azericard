package az.azericard.serviceuser.controller;

import az.azericard.core.domain.TokenType;
import az.azericard.core.exception.TokenValidityException;
import az.azericard.serviceuser.domain.dto.LoginDto;
import az.azericard.serviceuser.domain.dto.RegisterDto;
import az.azericard.serviceuser.domain.dto.UserDto;
import az.azericard.serviceuser.security.jwt.TokenProvider;
import az.azericard.serviceuser.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    private final UserService userService;
    private final AuthenticationManagerBuilder authManagerBuilder;
    private final TokenProvider tokenProvider;

    public AuthenticationController(
            UserService userService,
            AuthenticationManagerBuilder authManagerBuilder,
            TokenProvider tokenProvider
    ) {
        this.userService = userService;
        this.authManagerBuilder = authManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterDto registerDto) {
        LOG.debug("Request to register a new user with username: {}", registerDto.getUsername());

        UserDto user = userService.registerUser(registerDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@Valid @RequestBody LoginDto loginDTO) {
        LOG.debug("Request to authenticate user {}", loginDTO.username());

        userService.checkLoginCredentials(loginDTO);

        var authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.username(),
                loginDTO.password()
        );

        Authentication authentication = authManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map.Entry<String, Date> accessTokenInfo = tokenProvider.generateAccessToken(authentication);
        String accessToken = accessTokenInfo.getKey();
        Date accessTokenValidity = accessTokenInfo.getValue();

        Map.Entry<String, Date> refreshTokenInfo = tokenProvider.generateRefreshToken(authentication);
        String refreshToken = refreshTokenInfo.getKey();
        Date refreshTokenValidity = refreshTokenInfo.getValue();

        var token = new JwtToken(accessToken, accessTokenValidity, refreshToken, refreshTokenValidity);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    @PostMapping("/token/refresh")
    public ResponseEntity<JwtToken> getToken(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        LOG.debug("Request to get access token with refresh token");

        var refreshToken = refreshTokenDto.refreshToken();

        var tokenValidityResponse = tokenProvider.checkTokenValidity(refreshToken);
        if (!tokenValidityResponse.isValid()) {
            throw new TokenValidityException(TokenType.REFRESH_TOKEN, tokenValidityResponse.code());
        }

        Claims claims = tokenProvider.getClaims(refreshToken);
        if (!claims.get(TokenProvider.TOKEN_TYPE).equals(TokenType.REFRESH_TOKEN.toString())) {
            throw new TokenValidityException(TokenType.REFRESH_TOKEN, "Provided token is not a refresh token!");
        }

        Date refreshTokenValidity = claims.getExpiration();
        Authentication authentication = tokenProvider.getAuthentication(claims);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map.Entry<String, Date> accessTokenInfo = tokenProvider.generateAccessToken(authentication);
        String accessToken = accessTokenInfo.getKey();
        Date accessTokenValidity = accessTokenInfo.getValue();

        var token = new JwtToken(accessToken, accessTokenValidity, refreshToken, refreshTokenValidity);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    public record RefreshTokenDto(
            @NotBlank(message = "Refresh token can not be null or empty!")
            String refreshToken
    ) {
    }

    public record JwtToken(String accessToken, Date accessTokenExpiresIn,
                           String refreshToken, Date refreshTokenExpiresIn) {

        @Override
        public String toString() {
            return "JWTToken";
        }
    }
}
