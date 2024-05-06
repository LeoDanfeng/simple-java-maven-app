package com.mycompany.app.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@Slf4j
public class AppAuditingAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        log.info("CurrentAuditor: {}", Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName()));
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
