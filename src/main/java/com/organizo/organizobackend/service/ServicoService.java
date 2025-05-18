package com.organizo.organizobackend.service;

import com.organizo.organizobackend.dto.ServicoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Contrato da camada de serviço para Servico.
 * Define operações de listagem e consulta por ID.
 */
public interface ServicoService {

    /**
     * Retorna uma página de serviços cadastrados.
     * @param pageable parâmetros de paginação e ordenação
     */
    Page<ServicoDTO> listar(Pageable pageable);

    /**
     * Busca um serviço pelo seu ID.
     * @param id identificador do serviço
     * @return DTO do serviço encontrado
     */
    ServicoDTO buscarPorId(Long id);

    ServicoDTO criar(ServicoDTO dto);
    void deletar(Long id);
    ServicoDTO atualizar(Long id, ServicoDTO dto);
    ServicoDTO criar(Long salaoId, ServicoDTO dto);

}
