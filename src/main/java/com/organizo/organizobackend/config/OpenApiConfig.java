package com.organizo.organizobackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração da documentação OpenAPI (Swagger) para a aplicação Organizo.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Organizo API",
                version = "v1",
                contact = @Contact(
                        name = "Maurício Antônio Theodoro Neto",
                        email = "mauricioantonionetinho@gmail.com"
                )
        )
)
public class OpenApiConfig {

    /**
     * Define as informações principais da especificação OpenAPI.
     */
    @Bean
    public OpenAPI organizoOpenApi() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Organizo Beauty API")
                        .version("1.0")
                        .description("API REST para gestão de salões, agendamentos e financeiros"));
    }
}
