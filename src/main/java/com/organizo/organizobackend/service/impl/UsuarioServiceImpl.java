package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.AuthResponse;
import com.organizo.organizobackend.dto.LoginRequest;
import com.organizo.organizobackend.dto.RegistroRequest;
import com.organizo.organizobackend.enums.Role;
import com.organizo.organizobackend.model.Usuario;
import com.organizo.organizobackend.repository.UsuarioRepository;
import com.organizo.organizobackend.service.UsuarioService;
import com.organizo.organizobackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Lógica de negócio para registro e login de usuário.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repo,
                              BCryptPasswordEncoder encoder,
                              JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse registrar(RegistroRequest dto) {
        Optional<Usuario> existente = repo.findByEmail(dto.getEmail());
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        // Converte string de role para enum
        Role papel = Role.valueOf(dto.getRole());
        Usuario u = new Usuario(dto.getEmail(), encoder.encode(dto.getSenha()), dto.getNome(), papel);
        Usuario salvo = repo.save(u);
        String token = jwtUtil.gerarToken(salvo.getEmail());
        return new AuthResponse(token, salvo.getEmail(), salvo.getNome(), salvo.getRole().name());
    }

    @Override
    public AuthResponse autenticar(LoginRequest dto) {
        Usuario u = repo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (!encoder.matches(dto.getSenha(), u.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }
        String token = jwtUtil.gerarToken(u.getEmail());
        return new AuthResponse(token, u.getEmail(), u.getNome(), u.getRole().name());
    }
}
