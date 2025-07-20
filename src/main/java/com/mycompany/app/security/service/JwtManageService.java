package com.mycompany.app.security.service;

import com.mycompany.app.exception.AppException;
import com.mycompany.app.security.config.JwtComponentConfig;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: 罗丹枫
 * @Description: token管理服务，颁发token,认证token
 * @CreatedAt: 2024/7/11 22:22
 */

@Slf4j
@Service
public class JwtManageService {

    @Autowired
    private JWSSigner jwsSigner;
    @Autowired
    private JWSVerifier jwsVerifier;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${spring.application.name}")
    private String appName;
    @Value("${app.jwt.prefix:bearer }")
    private String jwtPrefix;
    @Value("${app.jwt.ttl:30}")
    private Long jwtTtl;
    @Value("${app.jwt.refreshTtl:60}")
    private Long refreshTtl;

    /**
     * @author: 罗丹枫
     * @description: TODO
     * @createdAt: 2024/8/26 21:25
     * @params: [auth]
     * @return: java.lang.String
     */

    public String generateToken(Authentication auth) {
        JWTClaimsSet jwtClaimsSet = jwtClaimsSet(auth);
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(), jwtClaimsSet);
        try {
            signedJWT.sign(jwsSigner);
        } catch (JOSEException e) {
            throw new AppException("Failed to sign token ", e);
        }
        final String token = jwtPrefix + signedJWT.serialize();
        // 由Redis管理token生命周期
        stringRedisTemplate.opsForValue().set(jwtClaimsSet.getJWTID(), token, jwtTtl, TimeUnit.MINUTES);
        return token;
    }

    public JWTClaimsSet jwtClaimsSet(Authentication auth) {
        final List<String> authoritiesList = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        final Date issueTime = new Date();
        // issuer(iss) subject(sub) audience(aud) issueTime(iat) expirationTime(exp) notBeforeTime(nbt), jwtId(jti)
        return new JWTClaimsSet.Builder()
                .issuer(appName)
                .subject(auth.getName())
                .issueTime(issueTime)
                .notBeforeTime(issueTime)
                .jwtID(UUID.randomUUID().toString())
                .claim(JwtComponentConfig.AUTHORITIES_CLAIM, authoritiesList)
                .build();
    }

    public Authentication getAuthentication(String token) {
        UsernamePasswordAuthenticationToken authentication = null;
        final JWTClaimsSet jwtClaimsSet = getJWTClaimsSet(token);
        final String redisKey = jwtClaimsSet.getJWTID();
        if (!stringRedisTemplate.hasKey(redisKey)) {
            throw new AppException("Login has been expired.");
        }
        List<GrantedAuthority> authoritiesList = ((List<String>) jwtClaimsSet.getClaim(JwtComponentConfig.AUTHORITIES_CLAIM))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        authentication = new UsernamePasswordAuthenticationToken(jwtClaimsSet.getSubject(),
                null,
                authoritiesList
        );
        stringRedisTemplate.expire(redisKey, jwtTtl, TimeUnit.MINUTES);
        return authentication;
    }


    public JWTClaimsSet getJWTClaimsSet(String token) {
        JWTClaimsSet jwtClaimsSet = null;
        try {
            SignedJWT parse = SignedJWT.parse(token);
            if (parse.verify(jwsVerifier)) {
                jwtClaimsSet = parse.getJWTClaimsSet();
            }
        } catch (Exception e) {
            throw new AppException("Invalid token", e);
        }
        return jwtClaimsSet;
    }
}
