package com.mycompany.app.security.config;

import com.mycompany.app.security.filter.JwtPreAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.mycompany.app.security.config.AuthorizationUrl.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @Author: 罗丹枫
 * @Description: WEB安全设置，包括用户认证和权限校验
 * @CreatedAt: 2024/7/11 22:17
 */

@Slf4j
@RefreshScope
@Configuration
public class AppWebSecurityConfig {

    @Value("${app.security.disable:false}")
    private boolean securityDisable;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        if (securityDisable) {
            http.authorizeRequests()
                    .anyRequest().permitAll();
        } else {
            http.authorizeRequests()
                    .anyRequest().authenticated();
        }
        return http.build();
    }

    /**
     * SpringSecurity 权限继承机制
     * if A > B
     * then A has All authorizations that B has without explicit declaration.
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ADMIN > NORMAL > ANONYMOUS");
        return roleHierarchy;
    }

    @Bean
    public ApplicationRunner securityDebug() {
        return args -> {
            Map<String, SecurityFilterChain> chains = applicationContext.getBeansOfType(SecurityFilterChain.class);
            chains.forEach((name, chain) -> {
                DefaultSecurityFilterChain chain1 = (DefaultSecurityFilterChain) chain;
                log.debug("过滤器链: {}", name);
                log.debug("匹配器: {}", chain1.getRequestMatcher());
                log.debug("过滤器: {}", chain1.getFilters());
            });
        };
    }
}
