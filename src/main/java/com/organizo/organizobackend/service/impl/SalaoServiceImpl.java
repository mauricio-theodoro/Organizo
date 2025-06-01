package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.SalaoDTO;
import com.organizo.organizobackend.exception.ResourceNotFoundException;
import com.organizo.organizobackend.mapper.SalaoMapper;
import com.organizo.organizobackend.model.Salao;
import com.organizo.organizobackend.model.Usuario;
import com.organizo.organizobackend.repository.SalaoRepository;
import com.organizo.organizobackend.repository.UsuarioRepository;
import com.organizo.organizobackend.service.SalaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Implementação da camada de serviço para Salão.
 * Utiliza exceções customizadas para tratamento de erros.
 */
@Service
public class SalaoServiceImpl implements SalaoService {

    private final SalaoRepository salaoRepo;
    private final SalaoMapper mapper;
    private final UsuarioRepository usuarioRepo;

    @Autowired
    public SalaoServiceImpl(SalaoRepository salaoRepo,
                            SalaoMapper mapper,
                            UsuarioRepository usuarioRepo) {
        this.salaoRepo = salaoRepo;
        this.mapper = mapper;
        this.usuarioRepo = usuarioRepo;
    }

    /**
     * Lista salões de forma paginada.
     * Cache: 'saloes' -> página + tamanho + ordenação.
     */
    @Override
    public Page<SalaoDTO> listar(Pageable pageable) {
        return salaoRepo.findAll(pageable)
                .map(mapper::toDto);
    }

    /**
     * Busca um salão pelo ID.
     * Cache: armazena em 'saloes:id'.
     */
    @Override
    @Cacheable(value = "saloes", key = "#id")
    public SalaoDTO buscarPorId(Long id) {
        Salao salao = salaoRepo.findById(id)
                // Lança exceção específica se não encontrar
                .orElseThrow(() -> new ResourceNotFoundException("Salão", "ID", id));
        return mapper.toDto(salao);
    }

    /**
     * Cria novo salão e limpa cache de listagem.
     */
    @Override
    @CacheEvict(value = "saloes", allEntries = true)
    public SalaoDTO criar(SalaoDTO dto) {
        // 1) verificamos se existe o usuário (owner) enviado pelo front
        Usuario owner = usuarioRepo.findById(dto.getOwnerId())
                // Lança exceção específica se não encontrar
                .orElseThrow(() -> new ResourceNotFoundException("Usuário (owner)", "ID", dto.getOwnerId()));

        // 2) convertemos o DTO para entidade (MAPSTRIP ignora owner)
        Salao salaEntity = mapper.toEntity(dto);

        // 3) fazemos o vínculo explícito
        salaEntity.setOwner(owner);

        // 4) salvamos e retornamos como DTO
        Salao saved = salaoRepo.save(salaEntity);
        return mapper.toDto(saved);
    }

    /**
     * Atualiza um salão e limpa cache.
     */
    @Override
    @CacheEvict(value = {"saloes", "saloes::#id"}, allEntries = false) // Limpa cache geral e específico do ID
    public SalaoDTO atualizar(Long id, SalaoDTO dto) {
        // buscamos o salão já existente
        Salao existente = salaoRepo.findById(id)
                // Lança exceção específica se não encontrar
                .orElseThrow(() -> new ResourceNotFoundException("Salão", "ID", id));

        // atualizamos campos simples
        existente.setNome(dto.getNome());
        existente.setCnpj(dto.getCnpj());
        existente.setEndereco(dto.getEndereco());
        existente.setTelefone(dto.getTelefone());

        // (opcional) permitir trocar owner? Geralmente não trocamos, mas se quiser:
        if (dto.getOwnerId() != null && !dto.getOwnerId().equals(existente.getOwner().getId())) {
            Usuario novoOwner = usuarioRepo.findById(dto.getOwnerId())
                    // Lança exceção específica se não encontrar
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário (novo owner)", "ID", dto.getOwnerId()));
            existente.setOwner(novoOwner);
        }

        Salao saved = salaoRepo.save(existente);
        return mapper.toDto(saved);
    }

    /**
     * Deleta um salão e limpa cache.
     */
    @Override
    @CacheEvict(value = {"saloes", "saloes::#id"}, allEntries = false) // Limpa cache geral e específico do ID
    public void deletar(Long id) {
        // Verifica se o salão existe antes de tentar deletar
        if (!salaoRepo.existsById(id)) {
            throw new ResourceNotFoundException("Salão", "ID", id);
        }
        salaoRepo.deleteById(id);
    }

    // O método toDTO privado foi removido pois o MapStruct está configurado para fazer a conversão.
}