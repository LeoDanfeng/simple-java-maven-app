package com.mycompany.app.security.service;

import com.mycompany.app.exception.AppException;
import com.mycompany.app.exception.ExpMsg;
import com.mycompany.app.exception.ThirdPartyException;
import com.mycompany.app.security.config.JwtComponentConfig;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JwtManageService {

    private JWSSigner jwsSigner;

    private JWSVerifier jwsVerifier;

    @Value("${jwt.prefix}")
    private String jwtPrefix;

    public JwtManageService(JWSSigner jwsSigner, JWSVerifier jwsVerifier) {
        this.jwsSigner = jwsSigner;
        this.jwsVerifier = jwsVerifier;
    }

    public String generateToken(Authentication auth) {
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(), jwtClaimsSet(auth));
        try {
            signedJWT.sign(jwsSigner);
        } catch (JOSEException e) {
            throw new AppException(ExpMsg.TOKEN_GENERATION_FAILURE.getExpMsg(), e);
        }
        return jwtPrefix + signedJWT.serialize();
    }

    private JWTClaimsSet jwtClaimsSet(Authentication auth) {
        List<String> authoritiesList = auth.getAuthorities().stream().map(t -> t.getAuthority()).collect(Collectors.toList());
        final long millis = System.currentTimeMillis();
        Date issueTime = new Date(millis);
        // 有效期半小时
        Date expireTime = new Date(millis + 30 * 60 * 1000);
        return new JWTClaimsSet.Builder()
                .issuer("my-app")
                .subject(auth.getName())
//                .audience("client")
                .issueTime(issueTime)
                .notBeforeTime(issueTime)
                .expirationTime(expireTime)
                .jwtID(UUID.randomUUID().toString())
                .claim(JwtComponentConfig.AUTHORITIES_CLAIM, authoritiesList)
                .build();
    }

    public String refreshToken(String token) {
        String newToken = token;
        if (needRefresh(token)) {
            Authentication authentication = getAuthentication(token);
            newToken = generateToken(authentication);
        }
        return newToken;
    }

    public Authentication getAuthentication(String token) {
        UsernamePasswordAuthenticationToken authentication = null;
        JWTClaimsSet jwtClaimsSet = getJWTClaimsSet(token);
        if (jwtClaimsSet != null && jwtClaimsSet.getExpirationTime().after(new Date())) {
            List<GrantedAuthority> authoritiesList = ((List<String>) jwtClaimsSet.getClaim(JwtComponentConfig.AUTHORITIES_CLAIM))
                    .stream()
                    .map(t -> new SimpleGrantedAuthority(t))
                    .collect(Collectors.toList());
            authentication = new UsernamePasswordAuthenticationToken(jwtClaimsSet.getSubject(),
                    null,
                    authoritiesList
            );
        }
        return authentication;
    }


    private JWTClaimsSet getJWTClaimsSet(String token) {
        JWTClaimsSet jwtClaimsSet = null;
        try {
            SignedJWT parse = SignedJWT.parse(token);
            if (parse.verify(jwsVerifier)) {
                jwtClaimsSet = parse.getJWTClaimsSet();
            }
        } catch (Exception e) {
            throw new ThirdPartyException(ExpMsg.TOKEN_PARSE_FAILURE.getExpMsg(), e);
        }
        return jwtClaimsSet;
    }

    private boolean needRefresh(String token) {
        return getJWTClaimsSet(token).getExpirationTime().before(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
    }

}
