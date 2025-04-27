package com.organizo.organizobackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Resposta com token JWT e informações do usuário.
 */
public class AuthResponse {

    @NotBlank
    private String token;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String role;

    public AuthResponse() { }

    public AuthResponse(String token, String email, String nome, String role) {
        this.token = token;
        this.email = email;
        this.nome = nome;
        this.role  = role;
    }

    // ===== Getters =====

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getRole()  {
        return role;
    }
}
