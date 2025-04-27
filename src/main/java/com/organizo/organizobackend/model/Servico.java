package com.organizo.organizobackend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um tipo de serviço oferecido
 * por um salão (ex: corte de cabelo, unhas, depilação).
 */
@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // PK

    @Column(nullable = false, length = 100)
    private String nome;            // Nome do serviço

    @Lob
    private String descricao;       // Descrição detalhada

    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos; // Duração em minutos

    @Column(nullable = false)
    private BigDecimal preco;       // Valor cobrado

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm; // Timestamp de criação

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm; // Timestamp de atualização

    public Servico() { }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
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

    @PrePersist
    protected void prePersist() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = criadoEm;
    }

    @PreUpdate
    protected void preUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
