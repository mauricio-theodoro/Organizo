package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.ServicoDTO;
import com.organizo.organizobackend.model.Servico;
import com.organizo.organizobackend.mapper.ServicoMapper;
import com.organizo.organizobackend.repository.ServicoRepository;
import com.organizo.organizobackend.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da camada de serviço para Servico,
 * contendo a lógica de conversão e uso do repositório.
 */
@Service
public class ServicoServiceImpl implements ServicoService {

    private final ServicoRepository servicoRepo;
    private final ServicoMapper mapper;

    @Autowired
    public ServicoServiceImpl(ServicoRepository servicoRepo, ServicoMapper mapper) {
        this.servicoRepo = servicoRepo;
        this.mapper = mapper;
    }

    /**
     * Lista serviços de forma paginada.
     * Cache "servicos" to reduce load on database.
     */
    @Override
    public Page<ServicoDTO> listar(Pageable pageable) {
        return servicoRepo.findAll(pageable)
                .map(mapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "servicos", key = "#id")
    public ServicoDTO buscarPorId(Long id) {
        Servico servico = servicoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + id));
        return mapper.toDto(servico);
    }

    @Override
    public ServicoDTO criar(ServicoDTO dto) {
        Servico entidade = mapper.toEntity(dto);
        Servico salvo = servicoRepo.save(entidade);
        return mapper.toDto(salvo);
    }

    @Override
    public void deletar(Long id) {
        servicoRepo.deleteById(id);
    }

    @Override
    public ServicoDTO atualizar(Long id, ServicoDTO dto) {
        Servico existente = servicoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        existente.setNome(dto.getNome());
        existente.setDescricao(dto.getDescricao());
        existente.setDuracaoMinutos(dto.getDuracaoMinutos());
        existente.setPreco(dto.getPreco());
        Servico salvado = servicoRepo.save(existente);
        return mapper.toDto(salvado);
    }

}
