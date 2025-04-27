package com.organizo.organizobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de Salão,
 * agora incluindo o campo cnpj.
 */
public class SalaoDTO {

    private Long id;

    @NotBlank(message = "nome é obrigatório")
    @Size(max = 100, message = "nome não pode exceder 100 caracteres")
    private String nome;

    @NotBlank(message = "cnpj é obrigatório")
    @Size(min = 14, max = 18, message = "cnpj deve ter tamanho válido")
    private String cnpj;

    @NotBlank(message = "endereco é obrigatório")
    private String endereco;

    @Size(max = 20, message = "telefone não pode exceder 20 caracteres")
    private String telefone;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    // ===== Getters & Setters =====

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
