package com.organizo.organizobackend.service;

import com.organizo.organizobackend.dto.ServicoDTO;
import java.util.List;

/**
 * Contrato da camada de serviço para Servico.
 * Define operações de listagem e consulta por ID.
 */
public interface ServicoService {

    /**
     * Retorna todos os serviços cadastrados.
     * @return lista de ServicoDTO
     */
    List<ServicoDTO> listarTodos();

    /**
     * Busca um serviço pelo seu ID.
     * @param id identificador do serviço
     * @return DTO do serviço encontrado
     */
    ServicoDTO buscarPorId(Long id);
}
