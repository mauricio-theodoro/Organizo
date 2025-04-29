package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.SalaoDTO;
import com.organizo.organizobackend.mapper.SalaoMapper;
import com.organizo.organizobackend.model.Salao;
import com.organizo.organizobackend.repository.SalaoRepository;
import com.organizo.organizobackend.service.SalaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da camada de serviço para Salão,
 * agora tratando o campo CNPJ.
 */
@Service
public class SalaoServiceImpl implements SalaoService {

    private final SalaoRepository salaoRepo;
    private final SalaoMapper mapper;

    @Autowired
    public SalaoServiceImpl(SalaoRepository salaoRepo, SalaoMapper mapper) {
        this.salaoRepo = salaoRepo;
        this.mapper = mapper;
    }

    @Override
    @Cacheable(value = "saloes", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public List<SalaoDTO> listarTodos() {
        return salaoRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SalaoDTO buscarPorId(Long id) {
        Salao salao = salaoRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Salão não encontrado com ID: " + id)
                );
        return toDTO(salao);
    }

    @Override
    @CacheEvict(value = "saloes", allEntries = true)
    public SalaoDTO criar(SalaoDTO dto) {
        // Converte DTO em entidade, agora incluindo CNPJ
        Salao salao = new Salao();
        salao.setNome(dto.getNome());
        salao.setCnpj(dto.getCnpj());
        salao.setEndereco(dto.getEndereco());
        salao.setTelefone(dto.getTelefone());

        // Persiste no banco
        Salao salvo = salaoRepo.save(salao);

        // Converte de volta para DTO
        return toDTO(salvo);
    }

    @Override
    @CacheEvict(value = "saloes", allEntries = true)
    public void deletar(Long id) {
        salaoRepo.deleteById(id);
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
