package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.ClienteDTO;
import com.organizo.organizobackend.mapper.ClienteMapper;
import com.organizo.organizobackend.model.Cliente;
import com.organizo.organizobackend.repository.ClienteRepository;
import com.organizo.organizobackend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ClienteMapper mapper;

    @Autowired
    public ClienteServiceImpl(ClienteRepository repo, ClienteMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    /**
     * Lista clientes paginados com cache.
     */
    @Override
    @Cacheable(value = "clientes", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<ClienteDTO> listar(Pageable pageable) {
        return repo.findAll(pageable)
                .map(mapper::toDto);
    }

    /**
     * Busca cliente por ID e armazena em cache.
     */
    @Override
    @Cacheable(value = "clientes", key = "#id")
    public ClienteDTO buscarPorId(Long id) {
        Cliente c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + id));
        return mapper.toDto(c);
    }

    /**
     * Cria novo cliente e limpa cache de lista.
     */
    @Override
    @CacheEvict(value = "clientes", allEntries = true)
    public ClienteDTO criar(ClienteDTO dto) {
        Cliente c = mapper.toEntity(dto);
        Cliente salvo = repo.save(c);
        return mapper.toDto(salvo);
    }

    /**
     * Deleta cliente por ID e limpa cache.
     */
    @Override
    @CacheEvict(value = "clientes", allEntries = true)
    public void deletar(Long id) {
        repo.deleteById(id);
    }

}
