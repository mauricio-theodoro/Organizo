package com.organizo.organizobackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Representa um salão de beleza.
 */
@Entity
@Table(name = "salao")
public class Salao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // Chave primaria

    @Column(nullable = false, length = 100)
    private String nome;            // Nome do salão

    @Column(length = 255)
    private String endereco;        // Endereço completo

    @Column(length = 20)
    private String telefone;        // Telefone para contato

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm; // Data de criação

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm; // Data de última atualização

    // Construtor vazio necessário pelo JPA
    public Salao() { }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }

    @PrePersist
    protected void prePersist() {
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = this.criadoEm;
    }

    @PreUpdate
    protected void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }
}
