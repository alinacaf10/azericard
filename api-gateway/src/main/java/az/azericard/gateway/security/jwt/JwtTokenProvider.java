package az.azericard.gateway.security.jwt;

import az.azericard.gateway.security.AppSecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * The class is used to manage JWT tokens.
 */
@Primary
@Component
public class JwtTokenProvider  {
    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);

    private static final String ISSUER = "AzeriCard MMC";
    private static final String AUDIENCE = "Project";

    private final JwtParser jwtParser;
    private final Key key;

    public JwtTokenProvider(AppSecurityProperties appSecurityProperties) {
        this.key = Keys.hmacShaKeyFor(appSecurityProperties.getTokenProperties().getBase64Secret().getBytes());
        this.jwtParser = Jwts.parserBuilder()
                .requireIssuer(ISSUER)
                .requireAudience(AUDIENCE)
                .setSigningKey(this.key)
                .build();
    }

    public TokenValidityResponse checkTokenValidity(String token) {
        try {
            jwtParser.parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            LOG.error("JWT Token expired: {}, {}", e, e.getMessage());
            return new TokenValidityResponse(false, "Token expired!");
        } catch (UnsupportedJwtException | MalformedJwtException | SecurityException | MissingClaimException |
                 IllegalArgumentException e) {
            LOG.error("Invalid JWT Token: {}, {}", e, e.getMessage());
            return new TokenValidityResponse(false, "Invalid token!");
        }

        return new TokenValidityResponse(true, null);
    }


}
