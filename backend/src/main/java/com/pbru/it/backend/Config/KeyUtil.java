package com.pbru.it.backend.Config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

public class KeyUtil {

    // ทำให้ constructor เป็น private เพื่อป้องกันการสร้าง instance ของ utility
    // class
    private KeyUtil() {
    }

    /**
     * โหลด Private Key จากไฟล์ PEM ที่เป็นฟอร์แมต PKCS#8.
     *
     * @param resource Spring Resource object ที่ชี้ไปยังไฟล์ private key (.pem)
     * @return อ็อบเจกต์ PrivateKey
     * @throws IllegalStateException ถ้าไม่สามารถโหลดหรือ parse key ได้
     */
    public static PrivateKey loadPrivateKey(Resource resource) {
        try {
            String keyString = readResourceToString(resource);

            String privateKeyPEM = keyString
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replaceAll(System.lineSeparator(), "")
                    .replace("-----END PRIVATE KEY-----", "");

            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return keyFactory.generatePrivate(keySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Failed to load private key from resource: " + resource.getFilename(), e);
        }
    }

    /**
     * โหลด Public Key จากไฟล์ PEM ที่เป็นฟอร์แมต X.509.
     *
     * @param resource Spring Resource object ที่ชี้ไปยังไฟล์ public key (.pem)
     * @return อ็อบเจกต์ PublicKey
     * @throws IllegalStateException ถ้าไม่สามารถโหลดหรือ parse key ได้
     */
    public static PublicKey loadPublicKey(Resource resource) {
        try {
            String keyString = readResourceToString(resource);

            String publicKeyPEM = keyString
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll(System.lineSeparator(), "")
                    .replace("-----END PUBLIC KEY-----", "");

            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return keyFactory.generatePublic(keySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Failed to load public key from resource: " + resource.getFilename(), e);
        }
    }

    /**
     * เมธอดเสริมสำหรับอ่านไฟล์ Resource ทั้งหมดมาเป็น String.
     * 
     * @param resource a Spring Resource
     * @return เนื้อหาของไฟล์ในรูปแบบ String
     * @throws IOException หากเกิดข้อผิดพลาดในการอ่านไฟล์
     */
    private static String readResourceToString(Resource resource) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }

}
