package com.mycompany.app.security.config;

import com.mycompany.app.security.filter.JwtAuthenticationFilter;
import com.mycompany.app.security.filter.JwtLoginFilter;
import com.mycompany.app.service.JwtLoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.mycompany.app.security.config.AuthorizationUrl.*;
import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class AppWebSecurityConfig {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtLoginService jwtLoginService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(ANONYMOUS.getAuthorities()).permitAll()
                .antMatchers(NORMAL.getAuthorities()).hasRole(NORMAL.getRole())
                .antMatchers(ADMIN.getAuthorities()).hasRole(ADMIN.getRole())
                .anyRequest().authenticated();
        http.addFilterBefore(new JwtAuthenticationFilter(jwtLoginService), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(new JwtLoginFilter(authenticationManager, jwtLoginService));
        http.formLogin();
        http.httpBasic();
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configurationSource);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ADMIN > NORMAL");
        return roleHierarchy;
    }
}
