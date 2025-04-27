package com.organizo.organizobackend.dto;

/**
 * Resposta com token JWT e informações do usuário.
 */
public class AuthResponse {

    private String token;
    private String email;
    private String nome;
    private String role;  // novo

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
