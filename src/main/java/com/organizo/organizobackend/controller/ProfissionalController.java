package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/profissionais")
    public ResponseEntity<List<ProfissionalDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /** GET /api/saloes/{salaoId}/profissionais */
    @GetMapping("/saloes/{salaoId}/profissionais")
    public ResponseEntity<List<ProfissionalDTO>> listarPorSalao(@PathVariable Long salaoId) {
        return ResponseEntity.ok(service.listarPorSalao(salaoId));
    }

    /** GET /api/profissionais/{id} */
    @GetMapping("/profissionais/{id}")
    public ResponseEntity<ProfissionalDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
