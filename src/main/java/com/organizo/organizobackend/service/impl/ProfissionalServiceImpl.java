package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.mapper.ProfissionalMapper;
import com.organizo.organizobackend.model.Profissional;
import com.organizo.organizobackend.model.Salao;
import com.organizo.organizobackend.model.Servico;
import com.organizo.organizobackend.repository.ProfissionalRepository;
import com.organizo.organizobackend.repository.SalaoRepository;
import com.organizo.organizobackend.repository.ServicoRepository;
import com.organizo.organizobackend.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementação da lógica de negócio para Profissional com paginação e cache.
 */
@Service
public class ProfissionalServiceImpl implements ProfissionalService {

    private final ProfissionalRepository repo;
    private final SalaoRepository salaoRepo;
    private final ServicoRepository servRepo;
    private final ProfissionalMapper mapper;

    @Autowired
    public ProfissionalServiceImpl(
            ProfissionalRepository repo,
            SalaoRepository salaoRepo,
            ServicoRepository servRepo,
            ProfissionalMapper mapper) {
        this.repo = repo;
        this.salaoRepo = salaoRepo;
        this.servRepo = servRepo;
        this.mapper = mapper;
    }

    /**
     * Lista profissionais com paginação.
     */
    @Override
    public Page<ProfissionalDTO> listar(Pageable pageable) {
        return repo.findAll(pageable)
                .map(mapper::toDto);
    }

    /**
     * Lista profissionais de um salão específico com paginação.
     */
    @Override
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

    /**
     * Cria um novo profissional dentro de um salão,
     * associando cargo e serviços.
     */
    @Override
    public ProfissionalDTO criar(Long salaoId, ProfissionalDTO dto) {
        Salao salao = salaoRepo.findById(salaoId)
                .orElseThrow(() -> new RuntimeException("Salão não encontrado: " + salaoId));

        Profissional entity = mapper.toEntity(dto);
        entity.setSalao(salao);
        // Carrega e associa serviços
        Set<Servico> servicos = dto.getServicoIds().stream()
                .map(id -> servRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + id)))
                .collect(Collectors.toSet());
        entity.setServicos(servicos);

        // O campo cargo já foi mapeado pelo MapStruct
        Profissional saved = repo.save(entity);
        return mapper.toDto(saved);
    }
}