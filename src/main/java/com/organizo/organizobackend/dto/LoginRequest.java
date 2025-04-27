package com.organizo.organizobackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Dados para autenticação de usuário.
 */
public class LoginRequest {

    @NotBlank(message = "email é obrigatório")
    @Email(message = "email deve ser válido")
    private String email;

    @NotBlank(message = "senha é obrigatória")
    private String senha;

    public LoginRequest() { }

    // ===== Getters & Setters =====

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
