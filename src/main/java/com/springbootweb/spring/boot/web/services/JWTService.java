package com.springbootweb.spring.boot.web.services;

import com.springbootweb.spring.boot.web.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JWTService {

    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
     return
             Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username",user.getUsername())
                 .claim("roles", Set.of("ADMIN" ,"USER"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60))
                .signWith(secretKey())
                .compact();

    }
    public String generateRefreshToken(User user) {
        return
                Jwts.builder()
                        .setSubject(user.getId().toString())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 1000L *60*60*24*30*6))
                        .signWith(secretKey())
                        .compact();

    }

    public Long getUserIdfromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey()) // your secretKey() method provides the key
                .build()
                .parseClaimsJws(token)
                .getBody();

        // subject was set as user.getId().toString() in generateToken()
        String userId = claims.getSubject();

        return Long.parseLong(userId);
    }

}
