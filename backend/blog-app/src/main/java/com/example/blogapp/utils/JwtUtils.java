package com.example.blogapp.utils;

import com.example.blogapp.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${jwt_secret}")
    private String secret_key;
    @Value("${jwt_expirationMs}")
    private long jwtExpirationMs;
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret_key.getBytes());
    }
    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .setExpiration(new Date(new Date().getTime()+jwtExpirationMs))
                .compact();
    }
    public String getEmailFromToken(String token){
        return getClaimFromToken(token,Claims::getSubject);
    }
    public Date getExpirationFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }
    public boolean validateToken(String token,User user){
        String email=getEmailFromToken(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token){
        return getExpirationFromToken(token).before(new Date());
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
