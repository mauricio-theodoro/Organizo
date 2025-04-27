package com.organizo.organizobackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO para Profissional.
 * Transfere dados básicos e identificador do salão.
 */
public class ProfissionalDTO {


    private Long id;

    @NotBlank(message = "nome é obrigatório")
    @Size(max = 50, message = "nome não pode exceder 50 caracteres")
    private String nome;

    @NotBlank(message = "sobrenome é obrigatório")
    @Size(max = 50, message = "sobrenome não pode exceder 50 caracteres")
    private String sobrenome;

    @NotBlank(message = "email é obrigatório")
    @Email(message = "email deve ser válido")
    private String email;

    @Size(max = 20, message = "telefone não pode exceder 20 caracteres")
    private String telefone;

    @NotNull(message = "salaoId é obrigatório")
    private Long salaoId;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Long getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Long salaoId) {
        this.salaoId = salaoId;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
