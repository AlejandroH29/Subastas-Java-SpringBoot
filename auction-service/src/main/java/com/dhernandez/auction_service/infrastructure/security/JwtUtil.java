package com.dhernandez.auction_service.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    public Key getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractAllClaims(String token){
        Key secret = getSigningKey();
        try {
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(secret)
                                .build()
                                    .parseClaimsJws(token)
                                    .getBody();
            return claims;
        } catch (ExpiredJwtException 
                | SignatureException 
                | MalformedJwtException 
                | UnsupportedJwtException 
                | IllegalArgumentException e ) {
            throw e;
        } 
    }

    public String extractUserId(String token){
        return extractAllClaims(token).getSubject();
    }

    public String extractEmail(String token){
        return extractAllClaims(token).get("email").toString();
    }

    public String extractRole(String token){
        return extractAllClaims(token).get("role").toString();
    }
    
    public boolean isTokenExpired(String token){
        if(extractAllClaims(token).getExpiration().before(new Date())){
            return true;
        }
        return false;
    }

    public boolean isTokenValid(String token){
        try {
            extractAllClaims(token);
            if(isTokenExpired(token)){
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
