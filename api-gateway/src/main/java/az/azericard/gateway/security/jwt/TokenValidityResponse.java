package az.azericard.gateway.security.jwt;

public record TokenValidityResponse(boolean isValid, String message) {
}
