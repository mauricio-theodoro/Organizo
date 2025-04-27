package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.ServicoDTO;
import com.organizo.organizobackend.model.Servico;
import com.organizo.organizobackend.repository.ServicoRepository;
import com.organizo.organizobackend.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ServicoServiceImpl(ServicoRepository servicoRepo) {
        this.servicoRepo = servicoRepo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ServicoDTO> listarTodos() {
        // Busca todas entidades e mapeia para DTO
        return servicoRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServicoDTO buscarPorId(Long id) {
        Servico servico = servicoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + id));
        return toDTO(servico);
    }

    /**
     * Converte entidade Servico em DTO.
     */
    private ServicoDTO toDTO(Servico s) {
        ServicoDTO dto = new ServicoDTO();
        dto.setId(s.getId());
        dto.setNome(s.getNome());
        dto.setDescricao(s.getDescricao());
        dto.setDuracaoMinutos(s.getDuracaoMinutos());
        dto.setPreco(s.getPreco());
        return dto;
    }
}
