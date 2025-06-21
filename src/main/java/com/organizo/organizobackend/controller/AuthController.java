package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.AuthResponse;
import com.organizo.organizobackend.dto.LoginRequest;
import com.organizo.organizobackend.dto.RegistroRequest;
import com.organizo.organizobackend.security.JwtTokenProvider;
import com.organizo.organizobackend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints para registro e login via JWT.
 */
@Tag(name = "Auth", description = "Endpoints para registro e login via JWT")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UsuarioService service;

    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;


    public AuthController(AuthenticationManager am,
                          JwtTokenProvider tp,
                          UsuarioService service) {
        this.authManager   = am;
        this.tokenProvider = tp;
        this.service   = service;
    }

    /** POST /api/auth/register */
    @Operation(summary = "Registra um novo usuário", description = "Cria usuário e retorna token JWT")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegistroRequest dto) {
        AuthResponse resp = service.registrar(dto);
        return ResponseEntity.status(201).body(resp);
    }

    /** POST /api/auth/login */
    @Operation(summary = "Autentica usuário", description = "Valida credenciais e retorna token JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse.AuthResponseDTO> login(
            @Valid @RequestBody LoginRequest rq   // <— usamos o DTO de login aqui
    ) {
        // 1) autentica
        var authToken = new UsernamePasswordAuthenticationToken(
                rq.getEmail(),                     // <— getEmail()
                rq.getSenha()                      // <— getSenha()
        );
        var auth = authManager.authenticate(authToken);

        // 2) gera token
        String token = tokenProvider.generateToken(auth);

        // 3) devolve só o token ao front
        return ResponseEntity.ok(new AuthResponse.AuthResponseDTO(token));
    }
}
