package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.mapper.ProfissionalMapper;
import com.organizo.organizobackend.model.Profissional;
import com.organizo.organizobackend.repository.ProfissionalRepository;
import com.organizo.organizobackend.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implementação da lógica de negócio para Profissional com paginação e cache.
 */
@Service
public class ProfissionalServiceImpl implements ProfissionalService {

    private final ProfissionalRepository repo;
    private final ProfissionalMapper mapper;

    @Autowired
    public ProfissionalServiceImpl(ProfissionalRepository repo, ProfissionalMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    /**
     * Lista profissionais com paginação.
     */
    @Override
    @Cacheable(value = "profissionais", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<ProfissionalDTO> listar(Pageable pageable) {
        return repo.findAll(pageable)
                .map(mapper::toDto);
    }

    /**
     * Lista profissionais de um salão específico com paginação.
     */
    @Override
    @Cacheable(value = "profissionais", key = "'salao-'+#salaoId+'-'+#pageable.pageNumber+'-'+#pageable.pageSize+'-'+#pageable.sort")
    public Page<ProfissionalDTO> listarPorSalao(Long salaoId, Pageable pageable) {
        return repo.findBySalaoId(salaoId, pageable)
                .map(mapper::toDto);
    }

    /**
     * Busca profissional por ID.
     */
    @Override
    @Cacheable(value = "profissionais", key = "#id")
    public ProfissionalDTO buscarPorId(Long id) {
        Profissional p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado: " + id));
        return mapper.toDto(p);
    }
}