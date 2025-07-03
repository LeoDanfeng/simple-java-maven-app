package com.mycompany.app.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: 罗丹枫
 * @Description: Swagger配置类
 * @DateTime: 2024/7/11 22:29
 */

@Configuration
@EnableSwagger2
public class AppSwaggerConfiguration {

    @Value("${server.port:8080}")
    private int port;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("App 接口文档")
                .description("App 接口文档")
                .termsOfServiceUrl("http://127.0.0.1:" + port + "/swagger")
                .version("1.0")
                .contact(new Contact("罗丹枫", "https://freshman.com", "13076894376@163.com"))
                .build();
    }
}
