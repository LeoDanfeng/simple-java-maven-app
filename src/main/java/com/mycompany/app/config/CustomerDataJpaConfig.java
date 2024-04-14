package com.mycompany.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.mycompany.app.dao")
public class CustomerDataJpaConfig {
}
