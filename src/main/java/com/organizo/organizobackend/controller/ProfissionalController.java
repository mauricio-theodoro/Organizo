package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints para Profissional.
 */
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
    @PreAuthorize("hasRole('PROFISSIONAL')")
    @GetMapping("/profissionais")
    public ResponseEntity<List<ProfissionalDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /** GET /api/saloes/{salaoId}/profissionais */
    /** Apenas DONO_SALAO e PROFISSIONAL podem listar profissionais de um salão */
    @PreAuthorize("hasAnyRole('DONO_SALAO','PROFISSIONAL')")
    @GetMapping("/saloes/{salaoId}/profissionais")
    public ResponseEntity<List<ProfissionalDTO>> listarPorSalao(@PathVariable Long salaoId) {
        return ResponseEntity.ok(service.listarPorSalao(salaoId));
    }

    /** GET /api/profissionais/{id} */
    /** Apenas PROFISSIONAL vê seus agendamentos ou dados */
    @PreAuthorize("hasRole('PROFISSIONAL')")
    @GetMapping("/profissionais/{id}")
    public ResponseEntity<ProfissionalDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
