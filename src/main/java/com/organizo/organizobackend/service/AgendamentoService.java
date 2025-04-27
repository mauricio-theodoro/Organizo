package com.organizo.organizobackend.service;

import com.organizo.organizobackend.dto.AgendamentoDTO;
import java.util.List;

/**
 * Contrato de negócio para Agendamento.
 */
public interface AgendamentoService {
    List<AgendamentoDTO> listarTodos();
    List<AgendamentoDTO> listarPorCliente(Long clienteId);
    List<AgendamentoDTO> listarPorProfissional(Long profissionalId);
    AgendamentoDTO criar(AgendamentoDTO dto);
    AgendamentoDTO confirmar(Long id);
    AgendamentoDTO cancelar(Long id);
}
