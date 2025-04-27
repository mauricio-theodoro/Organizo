package com.organizo.organizobackend.config;

import com.organizo.organizobackend.repository.UsuarioRepository;
import com.organizo.organizobackend.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

/**
 * Intercepta cada requisição, extrai e valida o JWT no header Authorization.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired private JwtUtil jwtUtil;
    @Autowired private UsuarioRepository repo;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {

        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String email = jwtUtil.obterEmail(token);
                // Se não estiver autenticado ainda
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    repo.findByEmail(email).ifPresent(user -> {
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        email, null, Collections.emptyList()
                                );
                        auth.setDetails(new WebAuthenticationDetailsSource()
                                .buildDetails(req));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    });
                }
            } catch (JwtException ex) {
                // token inválido ou expirado; ignore e prossiga sem auth
            }
        }
        chain.doFilter(req, res);
    }
}
