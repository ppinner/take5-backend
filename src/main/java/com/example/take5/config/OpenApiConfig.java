package com.example.take5.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI initOpenAPI() {
        return new OpenAPI().info(
                new Info().title("Take5 API")
                        .description("OpenAPI")
                        .version("v1.0")
        );
    }
}


