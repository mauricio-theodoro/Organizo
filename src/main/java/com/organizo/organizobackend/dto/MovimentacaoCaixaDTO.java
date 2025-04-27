package com.organizo.organizobackend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimentacaoCaixaDTO {

    private Long id;

    @NotNull(message = "salaoId é obrigatório")
    private Long salaoId;

    @NotNull(message = "profissionalId é obrigatório")
    private Long profissionalId;

    @NotNull(message = "agendamentoId é obrigatório")
    private Long agendamentoId;

    @NotNull(message = "valorTotal é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "valorTotal deve ser positivo")
    private BigDecimal valorTotal;

    @NotNull(message = "porcentagemProfissional é obrigatório")
    @Min(value = 0, message = "porcentagemProfissional deve ser >= 0")
    @Max(value = 100, message = "porcentagemProfissional deve ser <= 100")
    private Double porcentagemProfissional;

    @NotNull(message = "valorProfissional é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "valorProfissional deve ser positivo ou zero")
    private BigDecimal valorProfissional;

    @NotNull(message = "valorSalao é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "valorSalao deve ser positivo ou zero")
    private BigDecimal valorSalao;

    private LocalDateTime dataMovimentacao;

    public Long getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Long salaoId) {
        this.salaoId = salaoId;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Long getAgendamentoId() {
        return agendamentoId;
    }

    public void setAgendamentoId(Long agendamentoId) {
        this.agendamentoId = agendamentoId;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getPorcentagemProfissional() {
        return porcentagemProfissional;
    }

    public void setPorcentagemProfissional(Double porcentagemProfissional) {
        this.porcentagemProfissional = porcentagemProfissional;
    }

    public BigDecimal getValorProfissional() {
        return valorProfissional;
    }

    public void setValorProfissional(BigDecimal valorProfissional) {
        this.valorProfissional = valorProfissional;
    }

    public BigDecimal getValorSalao() {
        return valorSalao;
    }

    public void setValorSalao(BigDecimal valorSalao) {
        this.valorSalao = valorSalao;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}