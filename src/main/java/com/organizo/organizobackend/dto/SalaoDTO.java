package com.organizo.organizobackend.dto;

import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de Salão,
 * agora incluindo o campo cnpj.
 */
public class SalaoDTO {
    private Long id;
    private String nome;
    private String cnpj;               // Novo campo
    private String endereco;
    private String telefone;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    // ===== Getters & Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}
