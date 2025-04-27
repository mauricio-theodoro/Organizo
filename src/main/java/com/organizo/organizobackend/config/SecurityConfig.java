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
                // Desabilita CSRF para facilitar testes no Postman
                .csrf(csrf -> csrf.disable())

                // Define quem pode acessar o quê
                .authorizeHttpRequests(auth -> auth
                        // Liberar GET em todos os recursos de API
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                        // Liberar POST apenas para criar clientes
                        .requestMatchers(HttpMethod.POST, "/api/clientes").permitAll()

                        // **Liberar DELETE para clientes** (para testar remoção)
                        .requestMatchers(HttpMethod.DELETE, "/api/clientes/**").permitAll()

                        // Outras rotas continuam protegidas
                        .anyRequest().authenticated()
                )

                // Para rotas autenticadas, usar HTTP Basic (no futuro trocaremos por JWT)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
