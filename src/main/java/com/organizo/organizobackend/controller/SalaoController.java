package com.organizo.organizobackend.controller;


import com.organizo.organizobackend.dto.SalaoDTO;
import com.organizo.organizobackend.service.SalaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Endpoints REST para manipular Salões.
 */
@RestController
@RequestMapping("/api/saloes")
public class SalaoController {

    @Autowired
    private SalaoService salaoService;

    /** Lista todos os salões */
    /** Qualquer um (ou Cliente) pode ver salões */
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<SalaoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /** Busca salão por ID */
    /** Qualquer um pode ver detalhes do salão */
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<SalaoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /** Apenas DONO_SALAO pode criar um salão */
    @PreAuthorize("hasRole('DONO_SALAO')")
    @PostMapping
    public ResponseEntity<SalaoDTO> criar(@RequestBody SalaoDTO dto) {
        SalaoDTO criado = service.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    /** Deleta salão */
    /** Apenas DONO_SALAO pode deletar seu salão */
    @PreAuthorize("hasRole('DONO_SALAO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
