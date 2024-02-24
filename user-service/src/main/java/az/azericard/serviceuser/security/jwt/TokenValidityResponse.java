package az.azericard.serviceuser.security.jwt;

import az.azericard.core.exception.ErrorResponseCode;

public record TokenValidityResponse(boolean isValid, ErrorResponseCode code) {
}
