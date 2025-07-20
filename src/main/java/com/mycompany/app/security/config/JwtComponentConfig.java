package com.mycompany.app.security.config;

import com.mycompany.app.exception.AppException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;

/**
 * @Author: 罗丹枫
 * @Description: token的签名和校验
 * @CreatedAt: 2024/7/11 22:20
 */

@Configuration
@Slf4j
public class JwtComponentConfig {

    public static final String AUTHORIZATION_HEAD = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORITIES_CLAIM = "auth";

    @Bean
    public JWSSigner jwsSigner() {
        try {
            RSAKey privateKey = (RSAKey) JWK.parseFromPEMEncodedObjects(readKeyAsString("keypair/rsa_private_key.pem"));
            return new RSASSASigner(privateKey);
        } catch (JOSEException e) {
            throw new AppException("Wrong format for private key file", e);
        }
    }

    @Bean
    public JWSVerifier jwsVerifier() {
        try {
            RSAKey publicKey = (RSAKey) JWK.parseFromPEMEncodedObjects(readKeyAsString("keypair/rsa_public_key.pem"));
            return new RSASSAVerifier(publicKey);
        } catch (JOSEException e) {
            throw new AppException("Wrong format for public key file", e);
        }
    }


    private String readKeyAsString(String signatureFile) {
        String pemString;
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(signatureFile)) {
            pemString = new String(is.readAllBytes());
        } catch (IOException e) {
            throw new AppException("An IO exception occurred while Reading key file", e);
        }
        return pemString;
    }
}
