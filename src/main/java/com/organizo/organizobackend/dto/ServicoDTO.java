package com.organizo.organizobackend.dto;

import java.math.BigDecimal;

/**
 * DTO para transferência de dados de Servico
 * entre client e servidor, sem expor a entidade JPA.
 */
public class ServicoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Integer duracaoMinutos;
    private BigDecimal preco;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
}
