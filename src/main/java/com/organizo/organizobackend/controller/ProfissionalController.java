package com.organizo.organizobackend.controller;

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

    /** GET /api/profissionais */
    /** Apenas PROFISSIONAL vê lista geral de profissionais */
    @Operation(summary = "Lista todos os profissionais", description = "Somente PROFISSIONAL")
    @PreAuthorize("hasRole('PROFISSIONAL')")
    @GetMapping("/profissionais")
    public ResponseEntity<Page<ProfissionalDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    /** GET /api/saloes/{salaoId}/profissionais */
    /** Apenas DONO_SALAO e PROFISSIONAL podem listar profissionais de um salão */
    @Operation(summary = "Lista profissionais de um salão",
            description = "Somente DONO_SALAO e PROFISSIONAL")
    @PreAuthorize("hasAnyRole('DONO_SALAO','PROFISSIONAL')")
    @GetMapping("/saloes/{salaoId}/profissionais")
    public ResponseEntity<Page<ProfissionalDTO>> listarPorSalao(
            @PathVariable Long salaoId,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) { // Adicione Pageable
        return ResponseEntity.ok(service.listarPorSalao(salaoId, pageable)); // Passe pageable
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
