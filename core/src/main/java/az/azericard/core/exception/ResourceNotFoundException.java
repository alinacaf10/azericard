package az.azericard.core.exception;


import static az.azericard.core.exception.ExceptionMessageUtil.resourceNotFoundExceptionWithId;

/**
 * The exception will be thrown when resource isn't found.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String entityName, Long id) {
        super(resourceNotFoundExceptionWithId(entityName, id));
    }
}
