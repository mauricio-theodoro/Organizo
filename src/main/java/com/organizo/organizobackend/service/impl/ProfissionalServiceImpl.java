package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.model.Profissional;
import com.organizo.organizobackend.repository.ProfissionalRepository;
import com.organizo.organizobackend.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da lógica de negócio para Profissional.
 */
@Service
public class ProfissionalServiceImpl implements ProfissionalService {

    private final ProfissionalRepository repo;

    @Autowired
    public ProfissionalServiceImpl(ProfissionalRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ProfissionalDTO> listarTodos() {
        return repo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfissionalDTO> listarPorSalao(Long salaoId) {
        return repo.findBySalaoId(salaoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProfissionalDTO buscarPorId(Long id) {
        Profissional p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado: " + id));
        return toDTO(p);
    }

    /**
     * Converte entidade Profissional em DTO.
     */
    private ProfissionalDTO toDTO(Profissional p) {
        ProfissionalDTO dto = new ProfissionalDTO();
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setSobrenome(p.getSobrenome());
        dto.setEmail(p.getEmail());
        dto.setTelefone(p.getTelefone());
        dto.setSalaoId(p.getSalao().getId());
        dto.setCriadoEm(p.getCriadoEm());
        dto.setAtualizadoEm(p.getAtualizadoEm());
        return dto;
    }
}
