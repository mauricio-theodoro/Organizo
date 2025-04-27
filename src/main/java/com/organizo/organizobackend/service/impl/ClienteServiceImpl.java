package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.ClienteDTO;
import com.organizo.organizobackend.model.Cliente;
import com.organizo.organizobackend.repository.ClienteRepository;
import com.organizo.organizobackend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da lógica de negócio para Cliente,
 * realiza conversão entre entidade e DTO e usa o repositório.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;

    @Autowired
    public ClienteServiceImpl(ClienteRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<ClienteDTO> listarTodos() {
        return repo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO buscarPorId(Long id) {
        Cliente c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + id));
        return toDTO(c);
    }

    @Override
    public ClienteDTO criar(ClienteDTO dto) {
        // Converte DTO em entidade
        Cliente c = new Cliente();
        c.setNome(dto.getNome());
        c.setSobrenome(dto.getSobrenome());
        c.setEmail(dto.getEmail());
        c.setTelefone(dto.getTelefone());
        // Persiste e retorna DTO atualizado (com ID e timestamps)
        Cliente salvo = repo.save(c);
        return toDTO(salvo);
    }

    @Override
    public void deletar(Long id) {
        repo.deleteById(id);
    }

    /** Converte entidade Cliente em DTO */
    private ClienteDTO toDTO(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(c.getId());
        dto.setNome(c.getNome());
        dto.setSobrenome(c.getSobrenome());
        dto.setEmail(c.getEmail());
        dto.setTelefone(c.getTelefone());
        dto.setCriadoEm(c.getCriadoEm());
        dto.setAtualizadoEm(c.getAtualizadoEm());
        return dto;
    }
}
