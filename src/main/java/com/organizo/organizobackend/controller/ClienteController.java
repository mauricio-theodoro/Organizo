package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.ClienteDTO;
import com.organizo.organizobackend.dto.PaginatedResponse;
import com.organizo.organizobackend.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para manipular Clientes.
 */
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    @Autowired
    public ClienteController(ClienteService service) {
        this.service = service;
    }

    /**
     * GET /api/clientes?page=0&size=10
     * Retorna página de clientes (somente CLIENTE via @PreAuthorize)
     */
    @Operation(summary = "Lista clientes paginados", description = "Somente CLIENTE")
    @GetMapping
    public ResponseEntity<PaginatedResponse<ClienteDTO>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        Page<ClienteDTO> page = service.listar(pageable);

        // Monta resposta paginada genérica
        PaginatedResponse<ClienteDTO> resp = new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return ResponseEntity.ok(resp);
    }
    /** GET /api/clientes/{id} */
    /** Somente CLIENTE pode ver seu próprio cadastro */
    @Operation(summary = "Busca cliente por ID", description = "Somente CLIENTE")
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /** POST /api/clientes */
    /** Qualquer um pode se cadastrar como cliente */
    @Operation(summary = "Cria um novo cliente", description = "Registrado sem autenticação prévia")
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@Valid @RequestBody ClienteDTO dto) {
        ClienteDTO criado = service.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    /** DELETE /api/clientes/{id} */
    @Operation(summary = "Remove cliente", description = "Somente CLIENTE pode deletar sua conta")
    @PreAuthorize("hasRole('CLIENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
