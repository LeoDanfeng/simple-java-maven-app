package com.mycompany.app.security.config;

import com.mycompany.app.security.filter.JwtPreAuthenticationFilter;
import com.mycompany.app.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.mycompany.app.security.config.AuthorizationUrl.*;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class AppWebSecurityConfig {

    @Resource
    private JwtPreAuthenticationFilter jwtPreAuthenticationFilter;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(ANONYMOUS.getAuthorities()).permitAll()
                .antMatchers(NORMAL.getAuthorities()).hasAuthority(NORMAL.getRole())
                .antMatchers(ADMIN.getAuthorities()).hasAuthority(ADMIN.getRole())
                .anyRequest().authenticated();
        http.addFilterBefore(jwtPreAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilter(jwtAuthenticationFilter);
        http.formLogin();
        http.httpBasic();
//        http.exceptionHandling()
//                .authenticationEntryPoint(new AppEntryPoint());
        http.logout()
                .disable();
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ADMIN > NORMAL");
        return roleHierarchy;
    }
}
