package com.organizo.organizobackend.dto;

import java.time.LocalDateTime;

/**
 * DTO para Profissional.
 * Transfere dados básicos e identificador do salão.
 */
public class ProfissionalDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private Long salaoId;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Long getSalaoId() { return salaoId; }
    public void setSalaoId(Long salaoId) { this.salaoId = salaoId; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}
