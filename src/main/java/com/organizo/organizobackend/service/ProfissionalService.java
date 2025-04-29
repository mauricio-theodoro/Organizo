package com.organizo.organizobackend.service;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Contrato de serviços para Profissional.
 */
public interface ProfissionalService {

    /**
     * Lista profissionais paginados.
     */
    Page<ProfissionalDTO> listar(Pageable pageable);

    /**
     * Lista profissionais de um salão específico.
     * @param salaoId ID do salão
     */
    Page<ProfissionalDTO> listarPorSalao(Long salaoId, Pageable pageable);

    /**
     * Busca um profissional pelo seu ID.
     * @param id ID do profissional
     */
    ProfissionalDTO buscarPorId(Long id);
}
