package com.mycompany.app.security.config;

import com.mycompany.app.exception.AppException;
import com.mycompany.app.exception.ExpMsg;
import com.mycompany.app.security.service.JwtManageService;
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
            throw new AppException(ExpMsg.JWK_PARSE_FAILURE.getExpMsg(),e);
        }
    }

    @Bean
    public JWSVerifier jwsVerifier() {
        try {
            RSAKey publicKey = (RSAKey) JWK.parseFromPEMEncodedObjects(readKeyAsString("rsa/rsa_public_key.pem"));
            return new RSASSAVerifier(publicKey);
        } catch (JOSEException e) {
            throw new AppException(ExpMsg.JWK_PARSE_FAILURE.getExpMsg(),e);
        }
    }

    @Bean
    public JwtManageService jwtLoginService() {
        return new JwtManageService(jwsSigner(), jwsVerifier());
    }


    private String readKeyAsString(String signatureFile) {
        String pemString = null;
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(signatureFile)) {
            pemString = new String(is.readAllBytes());
        } catch (IOException e) {
            throw new AppException("读取密钥文件IO异常", e);
        }
        return pemString;
    }
}
