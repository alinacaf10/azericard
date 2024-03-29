package az.azericard.core.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * The class is used to handle {@link AuthenticationException} in filter levels. The
 * exception handling is delegated to {@link GlobalExceptionHandler}.
 */
@Component
public class UnAuthorizedExceptionHandler implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public UnAuthorizedExceptionHandler(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}
