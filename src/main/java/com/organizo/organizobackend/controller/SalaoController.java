package com.organizo.organizobackend.controller;


import com.organizo.organizobackend.dto.SalaoDTO;
import com.organizo.organizobackend.service.SalaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    @GetMapping
    public ResponseEntity<List<SalaoDTO>> listar() {
        return ResponseEntity.ok(salaoService.listarTodos());
    }

    /** Busca salão por ID */
    @GetMapping("/{id}")
    public ResponseEntity<SalaoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(salaoService.buscarPorId(id));
    }

    /** Cria novo salão */
    @PostMapping
    public ResponseEntity<SalaoDTO> criar(@Validated @RequestBody SalaoDTO dto) {
        SalaoDTO criado = salaoService.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    /** Deleta salão */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        salaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
