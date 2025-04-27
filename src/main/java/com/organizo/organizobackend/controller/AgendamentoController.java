package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.AgendamentoDTO;
import com.organizo.organizobackend.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/clientes/{id}/agendamentos")
    public ResponseEntity<List<AgendamentoDTO>> listarPorCliente(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorCliente(id));
    }

    /** GET /api/profissionais/{id}/agendamentos */
    @GetMapping("/profissionais/{id}/agendamentos")
    public ResponseEntity<List<AgendamentoDTO>> listarPorProfissional(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorProfissional(id));
    }

    /** POST /api/agendamentos */
    @PostMapping("/agendamentos")
    public ResponseEntity<AgendamentoDTO> criar(@RequestBody AgendamentoDTO dto) {
        AgendamentoDTO criado = service.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    /** PUT /api/agendamentos/{id}/confirmar */
    @PutMapping("/agendamentos/{id}/confirmar")
    public ResponseEntity<AgendamentoDTO> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(service.confirmar(id));
    }

    /** PUT /api/agendamentos/{id}/cancelar */
    @PutMapping("/agendamentos/{id}/cancelar")
    public ResponseEntity<AgendamentoDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
}
