package com.organizo.organizobackend.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI organizoOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Organizô Beauty API")
                        .version("1.0")
                        .description("API REST para gestão de salões, agendamentos e financeiros"));
    }
}