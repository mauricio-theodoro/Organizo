package com.organizo.organizobackend.config;

import com.organizo.organizobackend.config.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração de segurança da aplicação.
 * Utiliza JWT para proteger endpoints e deixa públicos somente os necessários.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // auth endpoints
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()

                        // public GETs
                        .requestMatchers(HttpMethod.GET, "/api/servicos/**", "/api/saloes/**").permitAll()

                        // registro de cliente e agendamento
                        .requestMatchers(HttpMethod.POST, "/api/clientes", "/api/agendamentos").permitAll()

                        // endpoints por role
                        .requestMatchers(HttpMethod.GET, "/api/clientes/**")
                        .hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/api/profissionais/**")
                        .hasRole("PROFISSIONAL")
                        .requestMatchers(HttpMethod.GET, "/api/saloes/**")
                        .hasRole("DONO_SALAO")

                        // operações de agendamento só para cliente
                        .requestMatchers(HttpMethod.PUT, "/api/agendamentos/**")
                        .hasRole("CLIENTE")

                        // restantes requerem autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
