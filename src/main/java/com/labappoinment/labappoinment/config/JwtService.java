package com.labappoinment.labappoinment.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private String secret = "R82T4J1VgFyvCaygj9ki7/sC3KjCyvMcV1wvJzLvm4oaofN/6nJnpC/Z6R1qJx8+";
    private static final long EXPIRATION_TIME_MS = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

    public String extractUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (token == null) {
            return false;
        }
        String username = extractUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpire(token));
    }

    private boolean isTokenExpire(String token) {
        return extraExpirationTime(token).before(new Date());
    }

    private Date extraExpirationTime(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails, Map<String, Object> extractClaims) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimDecoder) {
        Claims claims = extractAllClaims(token);
        return claimDecoder.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
