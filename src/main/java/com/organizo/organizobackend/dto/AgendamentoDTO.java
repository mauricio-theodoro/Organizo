package com.organizo.organizobackend.dto;

import com.organizo.organizobackend.enums.StatusAgendamento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de Agendamento.
 */
public class AgendamentoDTO {
    private Long id;

    @NotNull(message = "clienteId é obrigatório")
    private Long clienteId;

    @NotNull(message = "profissionalId é obrigatório")
    private Long profissionalId;

    @NotNull(message = "servicoId é obrigatório")
    private Long servicoId;

    @NotNull(message = "dataHoraAgendada é obrigatório")
    @Future(message = "dataHoraAgendada deve ser no futuro")
    private LocalDateTime dataHoraAgendada;

    private StatusAgendamento status;

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }
    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Long getServicoId() {
        return servicoId;
    }
    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }

    public LocalDateTime getDataHoraAgendada() {
        return dataHoraAgendada;
    }
    public void setDataHoraAgendada(LocalDateTime dataHoraAgendada) {
        this.dataHoraAgendada = dataHoraAgendada;
    }

    public StatusAgendamento getStatus() {
        return status;
    }
    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
}
