package com.pbru.it.backend.Services;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
// โครงสร้างของ JWT 
// HEADER.PAYLOAD.SIGNATURE
// HEADER: ข้อมูลเกี่ยวกับการเข้ารหัส (alg, typ)
// PAYLOAD: Claims (ข้อมูลเกี่ยวกับผู้ใช้) เช่น username , email , uuid
// SIGNATURE: ใช้ตรวจสอบว่า Token ถูกแก้ไขหรือไม่

// Claims คือข้อมูล (data) ที่ฝังอยู่ใน Payload ของ JWT

@Service
public class JWTService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RSAPrivateKey privateKey;

    @Autowired
    private RSAPublicKey publicKey;

    @Value("${jwt.expiration.ms}")
    private long jwtExpiration;

    private Claims extractAllClaims(String token) {
        System.out.println(getPrivateKey());
        return Jwts.parser()
                .verifyWith(getPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        System.out.println(getPublicKey());
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getPrivateKey(), Jwts.SIG.RS384)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean validateToken(String token) {
        try {
            String username = extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return isTokenValid(token, userDetails);
        } catch (Exception e) {
            return false;
        }
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

}
