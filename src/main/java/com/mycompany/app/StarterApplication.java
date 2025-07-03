package com.mycompany.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * @Author: 罗丹枫
 * @Description: 应用启动入口类
 * @CreatedAt: 2024/7/11 22:24
 */

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
@EnableDiscoveryClient
@ConfigurationPropertiesScan
public class StarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }

}
