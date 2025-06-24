package com.pbru.it.backend.Services;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// โครงสร้างของ JWT 
// HEADER.PAYLOAD.SIGNATURE

// HEADER: ข้อมูลเกี่ยวกับการเข้ารหัส (alg, typ)
// PAYLOAD: Claims (ข้อมูลเกี่ยวกับผู้ใช้) เช่น username , email , uuid
// SIGNATURE: ใช้ตรวจสอบว่า Token ถูกแก้ไขหรือไม่

// Claims คือข้อมูล (data) ที่ฝังอยู่ใน Payload ของ JWT

@Service
public class JWTService {

    // Secret Key เป็น Key ที่ Client เก็บไว้เพื่อให้ Server อ่าน
    private final String SECRET_KEY = "d7793e3ff165356afa7460d1874884b3ba378912a848825de5dae6dbba1bd2c2d9cbda92e0ace666f1704d29d667043f";

    private SecretKey getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    } 

    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(getSingInKey())
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                    .verifyWith(getSingInKey())
                    .build()
                    .parseSignedClaims(token.trim())
                    .getPayload();
    }

    public boolean isTokenValid(String token, String userEmail) {
        final String email = extractEmail(token);
        return email.equals(userEmail) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

}
