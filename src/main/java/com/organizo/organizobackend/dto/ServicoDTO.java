package com.organizo.organizobackend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO para transferência de dados de Servico
 * entre client e servidor, sem expor a entidade JPA.
 */
public class ServicoDTO {

    private Long id;

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @NotBlank(message = "descricao é obrigatório")
    private String descricao;

    @NotNull(message = "duracaoMinutos é obrigatório")
    @Min(value = 1, message = "duracaoMinutos deve ser pelo menos 1 minuto")
    private Integer duracaoMinutos;

    @NotNull(message = "preco é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "preco deve ser positivo")
    private BigDecimal preco;

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
}
