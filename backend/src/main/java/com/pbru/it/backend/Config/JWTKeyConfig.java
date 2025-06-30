package com.pbru.it.backend.Config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class JWTKeyConfig {

    // ให้ Spring ฉีด Resource object จาก path ใน application.properties เข้ามา
    @Value("${jwt.private.key}")
    private Resource privateKeyResource;

    @Value("${jwt.public.key}")
    private Resource publicKeyResource;

    @Bean
    public RSAPrivateKey privateKey() {
        // เรียกใช้เมธอดจาก KeyUtil โดยตรง
        return (RSAPrivateKey) KeyUtil.loadPrivateKey(privateKeyResource);
    }

    @Bean
    public RSAPublicKey publicKey() {
        // เรียกใช้เมธอดจาก KeyUtil โดยตรง
        return (RSAPublicKey) KeyUtil.loadPublicKey(publicKeyResource);
    }
    
}
