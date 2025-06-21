package com.organizo.organizobackend.security;

import com.organizo.organizobackend.model.Usuario;
import com.organizo.organizobackend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;
    public CustomUserDetailsService(UsuarioRepository repo) { this.repo = repo; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario u = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return User.builder()
                .username(u.getEmail())
                .password(u.getSenha())
                .roles(u.getRole().name())
                .build();
    }
}