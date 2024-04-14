package com.mycompany.app.security.config;

import com.mycompany.app.service.JwtLoginService;
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
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
@Slf4j
public class JwtComponentConfig {

    public static final String AUTHORIZATION_HEAD = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORITIES_CLAIM = "auth";

    @Bean
    public JWSSigner jwsSigner() {
        try {
            RSAKey privateKey = (RSAKey) JWK.parseFromPEMEncodedObjects(readKeyAsString("rsa/rsa_private_key.pem"));
            return new RSASSASigner(privateKey);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public JWSVerifier jwsVerifier() {
        try {
            RSAKey publicKey = (RSAKey) JWK.parseFromPEMEncodedObjects(readKeyAsString("rsa/rsa_public_key.pem"));
            return new RSASSAVerifier(publicKey);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public JwtLoginService jwtLoginService() {
        return new JwtLoginService(jwsSigner(),jwsVerifier());
    }


    private String readKeyAsString(String signatureFile) {
        String path =
                JwtComponentConfig.class.getClassLoader().getResource(signatureFile).getPath();
        String absolutePath = new File(path).getAbsolutePath();
        String pemString = null;
        try {
            pemString = new String(Files.readAllBytes(Path.of(absolutePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pemString;
    }
}
