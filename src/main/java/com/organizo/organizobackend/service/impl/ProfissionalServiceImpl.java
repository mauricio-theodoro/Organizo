package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.exception.ResourceNotFoundException;
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
        // 1) verificar se o salão existe
        Salao salao = salaoRepo.findById(salaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Salão não encontrado: " + salaoId));

        // 2) mapear DTO -> entidade (ignora serviços)
        Profissional prof = mapper.toEntity(dto);
        prof.setSalao(salao);

        // 3) salva sem atribuir ANY servicos ainda
        Profissional salvo = repo.save(prof);
        return mapper.toDto(salvo);
    }

    @Override
    public ProfissionalDTO vincularServicos(Long profissionalId, Set<Long> servicoIds) {
        Profissional p = repo.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado: " + profissionalId));

        Set<Servico> servicos = servicoIds.stream()
                .map(sid -> servRepo.findById(sid)
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + sid)))
                .collect(Collectors.toSet());
        p.setServicos(servicos);

        Profissional updated = repo.save(p);
        return mapper.toDto(updated);
    }

    @Override
    public ProfissionalDTO atualizar(Long id, ProfissionalDTO dto) {
        Profissional existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profissional não encontrado: " + id));

        existente.setNome(dto.getNome());
        existente.setSobrenome(dto.getSobrenome());
        existente.setEmail(dto.getEmail());
        existente.setTelefone(dto.getTelefone());
        existente.setCargo(dto.getCargo());
        // obs.: não tratamos servicos aqui

        Profissional atualizado = repo.save(existente);
        return mapper.toDto(atualizado);
    }

    @Override
    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Profissional não encontrado: " + id);
        }
        repo.deleteById(id);
    }
}