package com.lageraho.springsecurity.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtil {


    @Value("${jwt.secret.key}")
    private byte[] SECRET_KEY;
    @Value("${jwt.expiration.millis}")
    private long TOKEN_EXPIRATION;


    @PostConstruct
    private void init () {
        //Encode secret key in base-64
        this.SECRET_KEY = Base64Utils.encode(this.SECRET_KEY);
        log.debug ("Secret key encoded in Base-64 {}", this.SECRET_KEY);
    }

    public String extractUsername(String token) {
        return extractClaim (token, Claims::getSubject);
    }

    public Date extractExpiration (String token) {
        return extractClaim (token, Claims::getExpiration);
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims (token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims (String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired (String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken (UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken (claims, userDetails.getUsername());
    }

    private String createToken (Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date (System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken (String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
