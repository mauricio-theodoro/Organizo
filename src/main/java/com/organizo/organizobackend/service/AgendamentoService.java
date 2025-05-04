// src/main/java/com/organizo/organizobackend/service/AgendamentoService.java
package com.organizo.organizobackend.service;

import com.organizo.organizobackend.dto.AgendamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Contrato de negócio para Agendamento.
 */
public interface AgendamentoService {

    /**
     * Lista agendamentos de forma paginada.
     * @param pageable parâmetros de página, tamanho e ordenação
     * @return página de AgendamentoDTO
     */
    Page<AgendamentoDTO> listar(Pageable pageable);

    /**
     * Lista todos os agendamentos de um cliente.
     */
    List<AgendamentoDTO> listarPorCliente(Long clienteId);

    /**
     * Lista todos os agendamentos de um profissional.
     */
    List<AgendamentoDTO> listarPorProfissional(Long profissionalId);

    /**
     * Cria um novo agendamento.
     */
    AgendamentoDTO criar(AgendamentoDTO dto);

    /**
     * Confirma um agendamento existente.
     */
    AgendamentoDTO confirmar(Long id);

    /**
     * Cancela um agendamento existente.
     */
    AgendamentoDTO cancelar(Long id);
}
