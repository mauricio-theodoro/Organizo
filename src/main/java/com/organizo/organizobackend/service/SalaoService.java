package com.organizo.organizobackend.service;

import com.organizo.organizobackend.dto.SalaoDTO;
import java.util.List;

/**
 * Contrato da camada de serviço para Salão.
 * Define operações de CRUD e listagem de salões.
 */
public interface SalaoService {

    /**
     * Retorna todos os salões cadastrados.
     * @return lista de SalãoDTO
     */
    List<SalaoDTO> listarTodos();

    /**
     * Busca um salão pelo seu ID.
     * @param id identificador do salão
     * @return DTO do salão encontrado
     * @throws RuntimeException se não encontrar
     */
    SalaoDTO buscarPorId(Long id);

    /**
     * Cria um novo salão no sistema.
     * @param dto dados do salão a criar
     * @return DTO do salão criado (com ID gerado)
     */
    SalaoDTO criar(SalaoDTO dto);

    /**
     * Remove um salão existente.
     * @param id identificador do salão a remover
     */
    void deletar(Long id);
}
