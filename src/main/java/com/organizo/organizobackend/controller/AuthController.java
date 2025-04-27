package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.AuthResponse;
import com.organizo.organizobackend.dto.LoginRequest;
import com.organizo.organizobackend.dto.RegistroRequest;
import com.organizo.organizobackend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints para registro e login via JWT.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UsuarioService service;

    /** POST /api/auth/register */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegistroRequest dto) {
        AuthResponse resp = service.registrar(dto);
        return ResponseEntity.status(201).body(resp);
    }

    /** POST /api/auth/login */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest dto) {
        AuthResponse resp = service.autenticar(dto);
        return ResponseEntity.ok(resp);
    }
}
