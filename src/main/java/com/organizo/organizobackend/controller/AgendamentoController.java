package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.AgendamentoDTO;
import com.organizo.organizobackend.dto.PaginatedResponse;
import com.organizo.organizobackend.service.AgendamentoService;
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

/**
 * Endpoints REST para manipular Agendamentos.
 */
@Tag(name = "Agendamentos", description = "Operações de agendamento de serviços")
@RestController
@RequestMapping("/api")
public class AgendamentoController {

    @Autowired private AgendamentoService service;

    /**
     * GET /api/agendamentos?page=0&size=10
     * Lista todos os agendamentos de forma paginada.
     */
    @Operation(summary = "Lista agendamentos paginados", description = "Somente CLIENTE ou PROFISSIONAL")
    @GetMapping("/agendamentos")
    public ResponseEntity<PaginatedResponse<AgendamentoDTO>> listar(
            @PageableDefault(size = 10) Pageable pageable) {

        Page<AgendamentoDTO> page = service.listar(pageable);
        PaginatedResponse<AgendamentoDTO> resp = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
        return ResponseEntity.ok(resp);
    }

    /**
     * GET /api/clientes/{id}/agendamentos
     * Lista agendamentos de um cliente (paginação).
     */
    @Operation(summary = "Lista agendamentos de um cliente", description = "Somente CLIENTE")
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/clientes/{id}/agendamentos")
    public ResponseEntity<PaginatedResponse<AgendamentoDTO>> listarPorCliente(
            @PathVariable Long id,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<AgendamentoDTO> page = service.listarPorCliente(id, pageable);
        PaginatedResponse<AgendamentoDTO> resp = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
        return ResponseEntity.ok(resp);
    }

    /**
     * GET /api/profissionais/{id}/agendamentos
     * Lista agendamentos de um profissional (paginação).
     */
    @Operation(summary = "Lista agendamentos de um profissional", description = "Somente PROFISSIONAL")
    @PreAuthorize("hasRole('PROFISSIONAL')")
    @GetMapping("/profissionais/{id}/agendamentos")
    public ResponseEntity<PaginatedResponse<AgendamentoDTO>> listarPorProfissional(
            @PathVariable Long id,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<AgendamentoDTO> page = service.listarPorProfissional(id, pageable);
        PaginatedResponse<AgendamentoDTO> resp = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
        return ResponseEntity.ok(resp);
    }


    /** POST /api/agendamentos */
    /** Cliente cria agendamento */
    @Operation(summary = "Cria um novo agendamento", description = "Cliente agenda um serviço com data futura")
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/agendamentos")
    public ResponseEntity<AgendamentoDTO> criar(@Valid @RequestBody AgendamentoDTO dto) {
        return ResponseEntity.status(201).body(service.criar(dto));
    }

    /** PUT /api/agendamentos/{id}/confirmar */
    /** Cliente confirma */
    @Operation(summary = "Confirma agendamento", description = "Somente CLIENTE")
    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/agendamentos/{id}/confirmar")
    public ResponseEntity<AgendamentoDTO> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(service.confirmar(id));
    }

    /** Cliente cancela */
    @Operation(summary = "Cancela agendamento", description = "Somente CLIENTE")
    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/agendamentos/{id}/cancelar")
    public ResponseEntity<AgendamentoDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
}
