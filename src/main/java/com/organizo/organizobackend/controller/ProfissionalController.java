package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.PaginatedResponse;
import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints REST para manipular Profissionais com paginação.
 */
@Tag(name = "Profissionais", description = "Gestão dos profissionais dos salões")
@RestController
@RequestMapping("/api")
public class ProfissionalController {

    private final ProfissionalService service;

    @Autowired
    public ProfissionalController(ProfissionalService service) {
        this.service = service;
    }

    @Operation(summary = "Cria um novo profissional", description = "Somente DONO_SALAO")
    @PreAuthorize("hasRole('DONO_SALAO')")
    @PostMapping("/saloes/{salaoId}/profissionais")
    public ResponseEntity<ProfissionalDTO> criar(
            @PathVariable Long salaoId,
            @RequestBody ProfissionalDTO dto) {
        ProfissionalDTO criado = service.criar(salaoId, dto);
        return ResponseEntity.status(201).body(criado);
    }

    @Operation(summary = "Lista profissionais paginados", description = "Somente PROFISSIONAL")
    @PreAuthorize("hasRole('PROFISSIONAL')")
    @GetMapping
    public ResponseEntity<PaginatedResponse<ProfissionalDTO>> listar(
            @PathVariable Long salaoId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ProfissionalDTO> page = service.listarPorSalao(salaoId, pageable);
        PaginatedResponse<ProfissionalDTO> resp = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Lista profissionais de um salão", description = "Somente DONO_SALAO e PROFISSIONAL")
    @GetMapping("/saloes/{salaoId}/profissionais")
    public ResponseEntity<PaginatedResponse<ProfissionalDTO>> listarPorSalao(
            @PathVariable Long salaoId,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<ProfissionalDTO> page = service.listarPorSalao(salaoId, pageable);
        PaginatedResponse<ProfissionalDTO> resp = new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Busca profissional por ID", description = "Somente PROFISSIONAL")
    @PreAuthorize("hasRole('PROFISSIONAL')")
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> buscar(
            @PathVariable Long salaoId,
            @PathVariable Long id) {
        // salaoId validado implicitamente ou poderia checar se o profissional pertence ao salão
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}