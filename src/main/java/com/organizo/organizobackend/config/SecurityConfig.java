package com.organizo.organizobackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF pra facilitar testes com Postman
                .csrf(csrf -> csrf.disable())

                // Configura quais rotas ficam liberadas sem login
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                HttpMethod.GET,   "/api/clientes/**",
                                "/api/servicos/**",
                                "/api/profissionais/**",
                                "/api/saloes/**"
                        ).permitAll()

                        // Libera o POST para criar cliente sem autenticação
                        .requestMatchers(
                                HttpMethod.POST, "/api/clientes"
                        ).permitAll()

                        // Todas as outras rotas requerem autenticação (no futuro)
                        .anyRequest().authenticated()
                )

                // Habilita autenticação HTTP Basic para as rotas protegidas
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
