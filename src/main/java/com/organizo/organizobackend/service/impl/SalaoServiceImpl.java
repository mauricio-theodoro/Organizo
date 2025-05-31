package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.SalaoDTO;
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
 * Implementação da camada de serviço para Salão,
 * agora tratando o campo CNPJ.
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
                .orElseThrow(() -> new RuntimeException("Salão não encontrado: " + id));
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
                .orElseThrow(() -> new RuntimeException("Usuário (owner) não encontrado: " + dto.getOwnerId()));

        // 2) convertemos o DTO para entidade (MAPSTRIP ignora owner)
        Salao salaEntity = mapper.toEntity(dto);

        // 3) fazemos o vínculo explícito
        salaEntity.setOwner(owner);

        // 4) salvamos e retornamos como DTO
        Salao saved = salaoRepo.save(salaEntity);
        return mapper.toDto(saved);
    }

    /**
     * Deleta um salão e limpa cache.
     */
    @Override
    @CacheEvict(value = "saloes", allEntries = true)
    public void deletar(Long id) {
        salaoRepo.deleteById(id);
    }

    @Override
    public SalaoDTO atualizar(Long id, SalaoDTO dto) {
        // buscamos o salão já existente
        Salao existente = salaoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Salão não encontrado: " + id));

        // atualizamos campos simples
        existente.setNome(dto.getNome());
        existente.setCnpj(dto.getCnpj());
        existente.setEndereco(dto.getEndereco());
        existente.setTelefone(dto.getTelefone());

        // (opcional) permitir trocar owner? Geralmente não trocamos, mas se quiser:
        if (dto.getOwnerId() != null && !dto.getOwnerId().equals(existente.getOwner().getId())) {
            Usuario novoOwner = usuarioRepo.findById(dto.getOwnerId())
                    .orElseThrow(() -> new RuntimeException("Usuário (novo owner) não encontrado: " + dto.getOwnerId()));
            existente.setOwner(novoOwner);
        }

        Salao saved = salaoRepo.save(existente);
        return mapper.toDto(saved);
    }



    /**
     * Converte entidade Salao em DTO, incluindo CNPJ.
     */
    private SalaoDTO toDTO(Salao salao) {
        SalaoDTO dto = new SalaoDTO();
        dto.setId(salao.getId());
        dto.setNome(salao.getNome());
        dto.setCnpj(salao.getCnpj());
        dto.setEndereco(salao.getEndereco());
        dto.setTelefone(salao.getTelefone());
        dto.setCriadoEm(salao.getCriadoEm());
        dto.setAtualizadoEm(salao.getAtualizadoEm());
        return dto;
    }
}
