package com.hospital.hms.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    //JWT Secret from application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    //JWT Expiration from application.properties
    @Value("${jwt.expiration}")
    private long expirationTime;

    // Generate JWT token
    public String generateToken(String username){

        return Jwts.builder()

                .subject(username)

                .issuedAt(new Date())

                // Token valid for 1 day
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expirationTime))

                .signWith(
                        getSigningKey()
                )

                .compact();
    }

    // Extract username from token
    public String extractUsername(String token){

        return extractClaims(token)
                .getSubject();
    }

    // Validate token username
    public boolean validateToken(
            String token,
            String username){

        String extractedUsername =
                extractUsername(token);

        return extractedUsername
                .equals(username)

                && !isTokenExpired(token);
    }

    // Check token expiration
    private boolean isTokenExpired(String token){

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // Extract all JWT claims
    private Claims extractClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }
        //Generate signing key from secret (Helper Method)
        private SecretKey getSigningKey(){

            return Keys.hmacShaKeyFor(
                    secretKey.getBytes()
            );

    }
}