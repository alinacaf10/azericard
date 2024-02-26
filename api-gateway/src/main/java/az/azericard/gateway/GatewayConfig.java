package az.azericard.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    public GatewayConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://user-service"))
                .route("user-service", r -> r.path("/api/v1/users/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://user-service"))
                .route("payment-service", r -> r.path("/api/v1/payments/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://payment-service"))
                .route("card-service", r -> r.path("/api/v1/cards/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://card-service"))
                .route("product-service", r -> r.path("/api/v1/products/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://product-service"))
                .build();
    }
}
