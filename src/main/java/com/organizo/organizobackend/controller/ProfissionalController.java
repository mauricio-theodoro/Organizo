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

import java.util.List;

/**
 * Endpoints para Profissional.
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

    @Operation(summary = "Lista profissionais paginados", description = "Somente PROFISSIONAL")
    @GetMapping("/profissionais")
    public ResponseEntity<PaginatedResponse<ProfissionalDTO>> listarTodos(
            @PageableDefault(size = 10) Pageable pageable) {

        Page<ProfissionalDTO> page = service.listar(pageable);

        PaginatedResponse<ProfissionalDTO> resp = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Lista profissionais de um salão paginados",
            description = "Somente DONO_SALAO e PROFISSIONAL")
    @GetMapping("/saloes/{salaoId}/profissionais")
    public ResponseEntity<PaginatedResponse<ProfissionalDTO>> listarPorSalao(
            @PathVariable Long salaoId,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<ProfissionalDTO> page = service.listarPorSalao(salaoId, pageable);

        PaginatedResponse<ProfissionalDTO> resp = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
        return ResponseEntity.ok(resp);
    }

    /** GET /api/profissionais/{id} */
    /** Apenas PROFISSIONAL vê seus agendamentos ou dados */
    @Operation(summary = "Busca profissional por ID", description = "Somente PROFISSIONAL")
    @PreAuthorize("hasRole('PROFISSIONAL')")
    @GetMapping("/profissionais/{id}")
    public ResponseEntity<ProfissionalDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
