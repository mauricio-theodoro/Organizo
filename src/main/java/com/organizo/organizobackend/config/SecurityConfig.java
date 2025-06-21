package com.organizo.organizobackend.config;

import com.organizo.organizobackend.enums.Role;
import com.organizo.organizobackend.security.JwtAuthenticationFilter;
import com.organizo.organizobackend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração de segurança da aplicação.
 * Utiliza JWT e define permissões baseadas em URL/Método e Roles.
 * Habilita segurança a nível de método (@PreAuthorize) para regras mais finas.
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Habilita @PreAuthorize, @PostAuthorize
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;


    public SecurityConfig(JwtTokenProvider p, CustomUserDetailsService uds) {
        this.tokenProvider      = p;
        this.userDetailsService = uds;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var jwtFilter = new JwtAuthenticationFilter(tokenProvider, userDetailsService);
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Endpoints Públicos
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll() // Registro e Login
                        // Permitir visualização pública de salões, serviços e profissionais (para descoberta/agendamento)
                        .requestMatchers(HttpMethod.GET, "/api/saloes", "/api/saloes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/servicos", "/api/servicos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/profissionais", "/api/profissionais/**").permitAll()

                        // 2. Endpoints específicos para CLIENTE
                        .requestMatchers(HttpMethod.POST, "/api/agendamentos").hasRole(Role.CLIENTE.name())
                        // GET/PUT próprios dados (precisará de @PreAuthorize para garantir que é o próprio cliente)
                        .requestMatchers(HttpMethod.GET, "/api/clientes/**").hasRole(Role.CLIENTE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/clientes/**").hasRole(Role.CLIENTE.name())
                        // GET/Cancelar próprios agendamentos (precisará de @PreAuthorize)
                        .requestMatchers(HttpMethod.GET, "/api/agendamentos/cliente/**").hasRole(Role.CLIENTE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/agendamentos/{id}/cancelar").hasRole(Role.CLIENTE.name())

                        // 3. Endpoints específicos para PROFISSIONAL
                        // GET/PUT próprios dados (precisará de @PreAuthorize)
                        .requestMatchers(HttpMethod.GET, "/api/profissionais/me").hasRole(Role.PROFISSIONAL.name()) // Exemplo de endpoint "/me"
                        .requestMatchers(HttpMethod.PUT, "/api/profissionais/me").hasRole(Role.PROFISSIONAL.name())
                        // GET/Confirmar/Cancelar próprios agendamentos (precisará de @PreAuthorize)
                        .requestMatchers(HttpMethod.GET, "/api/agendamentos/profissional/**").hasRole(Role.PROFISSIONAL.name())
                        .requestMatchers(HttpMethod.PUT, "/api/agendamentos/{id}/confirmar").hasRole(Role.PROFISSIONAL.name())
                        .requestMatchers(HttpMethod.PUT, "/api/agendamentos/{id}/cancelar").hasRole(Role.PROFISSIONAL.name()) // Profissional também pode cancelar

                        // 4. Endpoints específicos para DONO_SALAO (Muitos exigirão @PreAuthorize para checar posse)
                        .requestMatchers(HttpMethod.POST, "/api/saloes").hasRole(Role.DONO_SALAO.name())
                        .requestMatchers(HttpMethod.PUT, "/api/saloes/**").hasRole(Role.DONO_SALAO.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/saloes/**").hasRole(Role.DONO_SALAO.name())
                        .requestMatchers(HttpMethod.POST, "/api/saloes/{salaoId}/profissionais").hasRole(Role.DONO_SALAO.name())
                        .requestMatchers(HttpMethod.PUT, "/api/profissionais/**").hasRole(Role.DONO_SALAO.name()) // Dono pode atualizar profissional do seu salão
                        .requestMatchers(HttpMethod.DELETE, "/api/profissionais/**").hasRole(Role.DONO_SALAO.name())
                        .requestMatchers(HttpMethod.POST, "/api/profissionais/{profId}/servicos").hasRole(Role.DONO_SALAO.name())
                        .requestMatchers(HttpMethod.POST, "/api/servicos").hasRole(Role.DONO_SALAO.name())
                        .requestMatchers(HttpMethod.PUT, "/api/servicos/**").hasRole(Role.DONO_SALAO.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/servicos/**").hasRole(Role.DONO_SALAO.name())
                        // Dono pode confirmar/cancelar agendamentos do seu salão (precisará de @PreAuthorize)
                        .requestMatchers(HttpMethod.PUT, "/api/agendamentos/{id}/confirmar").hasRole(Role.DONO_SALAO.name())
                        .requestMatchers(HttpMethod.PUT, "/api/agendamentos/{id}/cancelar").hasRole(Role.DONO_SALAO.name())

                        // 5. Endpoints específicos para ADMIN (Ou acesso total via @PreAuthorize)
                        // Exemplo: Permitir que ADMIN gerencie usuários
                        .requestMatchers("/api/usuarios/**").hasRole(Role.ADMIN.name())
                        // Exemplo: Permitir que ADMIN veja todos os agendamentos
                        .requestMatchers(HttpMethod.GET, "/api/agendamentos").hasRole(Role.ADMIN.name())

                        // 6. Qualquer outra requisição precisa estar autenticada
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

