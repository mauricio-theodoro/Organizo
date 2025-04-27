package com.organizo.organizobackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Dados para registro de novo usuário.
 */
public class RegistroRequest {

    @NotBlank @Email
    private String email;

    @NotBlank
    private String senha;

    @NotBlank
    private String nome;

    @NotNull
    private String role;    // recebido como String, mapeado para Role enum

    public RegistroRequest() { }

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

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
