package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.AgendamentoDTO;
import com.organizo.organizobackend.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Endpoints REST para manipular Agendamentos.
 */
@RestController
@RequestMapping("/api")
public class AgendamentoController {

    @Autowired private AgendamentoService service;

    /** GET /api/agendamentos */
    @GetMapping("/agendamentos")
    public ResponseEntity<List<AgendamentoDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /** GET /api/clientes/{id}/agendamentos */
    /** Cliente lista seus agendamentos */
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/clientes/{id}/agendamentos")
    public ResponseEntity<List<AgendamentoDTO>> listarPorCliente(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorCliente(id));
    }

    /** GET /api/profissionais/{id}/agendamentos */
    /** Profissional lista agendamentos */
    @PreAuthorize("hasRole('PROFISSIONAL')")
    @GetMapping("/profissionais/{id}/agendamentos")
    public ResponseEntity<List<AgendamentoDTO>> listarPorProfissional(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorProfissional(id));
    }


    /** POST /api/agendamentos */
    /** Cliente cria agendamento */
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/agendamentos")
    public ResponseEntity<AgendamentoDTO> criar(@RequestBody AgendamentoDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    /** PUT /api/agendamentos/{id}/confirmar */
    /** Cliente confirma */
    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/agendamentos/{id}/confirmar")
    public ResponseEntity<AgendamentoDTO> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(service.confirmar(id));
    }

    /** Cliente cancela */
    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/agendamentos/{id}/cancelar")
    public ResponseEntity<AgendamentoDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
}
