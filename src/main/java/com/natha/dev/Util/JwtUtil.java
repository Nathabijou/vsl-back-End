package com.natha.dev.Util;

import com.natha.dev.Configuration.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final SecretKey secretKey;
    private final String secretString;
    private static final int TOKEN_VALIDITY = 3600 * 5; // 5 hours

    public JwtUtil(JwtConfig jwtConfig) {
        this.secretString = jwtConfig.jwtSecret();
        this.secretKey = jwtConfig.jwtSecretKey();
        logger.info("JWT Util initialized with secret key length: {}", secretString.length());
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            logger.debug("Validating token with key algorithm: {}", secretKey.getAlgorithm());
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Error validating token: {}", e.getMessage());
            logger.debug("Token validation failed for token: {}", token);
            throw e;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Add user roles to claims with ROLE_ prefix
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(java.util.stream.Collectors.toList()));

        // Debug log the roles being added to the token
        logger.debug("Adding roles to JWT token: " + claims.get("roles"));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

//    public static void planMaintenance(LocalDate inputDate) {
//        LocalDate today = LocalDate.now();
//
//        if (inputDate.isBefore(today)) {
////            throw new IllegalArgumentException("The date provided is before today.");
//        }
//
//        System.out.println("Maintenance planned for: " + inputDate);
//    }


}
