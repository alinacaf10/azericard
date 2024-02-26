package az.azericard.gateway;

import az.azericard.gateway.security.AppSecurityProperties;
import az.azericard.gateway.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.lang.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter implements GatewayFilter {
    private final JwtTokenProvider tokenProvider;
    private final AppSecurityProperties securityProperties;

    public AuthenticationFilter(JwtTokenProvider tokenProvider, AppSecurityProperties securityProperties) {
        this.tokenProvider = tokenProvider;
        this.securityProperties = securityProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String token = this.resolveToken(request);

        if (!Strings.hasText(token) || !tokenProvider.checkTokenValidity(token).isValid()) {
            return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }


    /**
     * Returns token without <b>'Bearer'</b> prefix if token starts with prefix,
     * otherwise {@code null}
     *
     * @param request HTTP request
     * @return token without 'Bearer' prefix
     */
    private String resolveToken(ServerHttpRequest request) {
        var tokenProperties = securityProperties.getTokenProperties();

        HttpHeaders headers = request.getHeaders();
        List<String> authorizationHeaders = headers.get(tokenProperties.getAuthorizationHeaderText());
        if (authorizationHeaders == null || authorizationHeaders.get(0) == null) {
            return null;
        }

        String bearerToken = authorizationHeaders.get(0);
        String tokenPrefix = tokenProperties.getTokenPrefix();

        if (Strings.hasText(bearerToken) && bearerToken.startsWith(tokenPrefix)) {
            return bearerToken.substring(tokenPrefix.length());
        }

        return null;
    }
}
