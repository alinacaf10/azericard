package az.azericard.core.exception;


/**
 * The exception will be thrown when given parameters are invalid.
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
