package com.organizo.organizobackend.service;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import java.util.List;

/**
 * Contrato de serviços para Profissional.
 */
public interface ProfissionalService {

    /**
     * Lista todos os profissionais de todos os salões.
     */
    List<ProfissionalDTO> listarTodos();

    /**
     * Lista profissionais de um salão específico.
     * @param salaoId ID do salão
     */
    List<ProfissionalDTO> listarPorSalao(Long salaoId);

    /**
     * Busca um profissional pelo seu ID.
     * @param id ID do profissional
     */
    ProfissionalDTO buscarPorId(Long id);
}
