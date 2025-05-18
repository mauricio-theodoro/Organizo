package com.organizo.organizobackend.config;

import com.organizo.organizobackend.config.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1) Toda spec e JSON do OpenAPI
                        .requestMatchers("/v3/api-docs/**").permitAll()

                        // 2) Os recursos do Swagger UI (HTML, JS, CSS, fontes…)
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-ui/index.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // libere o endpoint de erro (fallback do Spring)
                        .requestMatchers("/error").permitAll()

                        // 3) Seus endpoints públicos
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET,  "/api/servicos/**", "/api/saloes/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/clientes", "/api/agendamentos").permitAll()

                        // 4) Demais regras por role…
                        .requestMatchers(HttpMethod.GET,  "/api/clientes/**").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.GET,  "/api/profissionais/**").hasRole("DONO_SALAO")
                        .requestMatchers(HttpMethod.GET,  "/api/saloes/**").hasRole("DONO_SALAO")
                        .requestMatchers(HttpMethod.PUT,  "/api/agendamentos/**").hasRole("CLIENTE")

                        // LISTAGEM DE PROFISSIONAIS: exato e com paginação
                        .requestMatchers(HttpMethod.GET,
                                "/api/profissionais",        // cobre GET /api/profissionais
                                "/api/profissionais/**"      // cobre GET /api/profissionais/{id} e qualquer subpath
                        ).hasRole("PROFISSIONAL")

                        // libera GET público:
                        .requestMatchers(HttpMethod.GET, "/api/servicos/**").permitAll()
                        // cria, atualiza, deleta só para dono do salão
                        .requestMatchers(HttpMethod.POST,   "/api/servicos").hasRole("DONO_SALAO")
                        .requestMatchers(HttpMethod.PUT,    "/api/servicos/**").hasRole("DONO_SALAO")
                        .requestMatchers(HttpMethod.DELETE, "/api/servicos/**").hasRole("DONO_SALAO")

                        // 5) Todo o resto precisa estar autenticado
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
