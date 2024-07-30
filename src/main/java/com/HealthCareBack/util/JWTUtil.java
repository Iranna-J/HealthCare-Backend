package com.HealthCareBack.util;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.HealthCareBack.dto.User;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(JWTUtil.class);
    private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 10; // 10 hours

    // Proper Key initialization using JWT's Keys utility
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Extracts a specific claim from the token using the provided claims resolver function.
     *
     * @param token the JWT token
     * @param claimsResolver the function to extract the desired claim from Claims
     * @param <T> the type of the extracted claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the expiration date from the token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the token.
     *
     * @param token the JWT token
     * @return all claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        logger.info("Attempting to parse JWT token");
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception e) {
            logger.error("Error parsing JWT token: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Checks if the token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates a JWT token containing only the user_id and role.
     *
     * @param user the user whose details are to be included in the token
     * @return the generated JWT token
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());
        claims.put("role", user.getRole());
        return createToken(claims);
    }

    /**
     * Creates a JWT token with the specified claims.
     *
     * @param claims the claims to be included in the token
     * @return the generated JWT token
     */
    private String createToken(Map<String, Object> claims) {
        logger.info("Creating token with claims: {}", claims);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        logger.info("Token created: {}", token);
        return token;
    }

    /**
     * Validates the token by checking if the token belongs to the user and is not expired.
     *
     * @param token the JWT token
     * @param user the user to validate against
     * @return true if the token is valid, false otherwise
     */
    public Boolean validateToken(String token, User user) {
        final int userIdFromToken = extractUserId(token);
        return (userIdFromToken == user.getId() && !isTokenExpired(token));
    }

    /**
     * Extracts the user ID from the token.
     *
     * @param token the JWT token
     * @return the extracted user ID, or -1 if extraction fails
     */
    public int extractUserId(String token) {
        try {
            if (isTokenExpired(token)) {
                logger.warn("Token has expired");
                return -1;
            }

            Claims claims = extractAllClaims(token);
            logger.info("Claims extracted from token: {}", claims);

            Integer userId = claims.get("user_id", Integer.class);
            logger.info("Extracted user_id: {}", userId);

            return userId != null ? userId : -1;
        } catch (Exception e) {
            logger.error("Error extracting userId from token: {}", e.getMessage());
            return -1;
        }
    }

    /**
     * Extracts the role from the token.
     *
     * @param token the JWT token
     * @return the extracted role, or null if extraction fails
     */
    public String extractRole(String token) {
        try {
            if (isTokenExpired(token)) {
                logger.warn("Token has expired");
                return null;
            }

            Claims claims = extractAllClaims(token);
            logger.info("Claims extracted from token: {}", claims);

            String role = claims.get("role", String.class);
            logger.info("Extracted role: {}", role);

            return role;
        } catch (Exception e) {
            logger.error("Error extracting role from token: {}", e.getMessage());
            return null;
        }
    }
}
