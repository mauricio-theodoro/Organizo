package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.SalaoDTO;
import com.organizo.organizobackend.service.SalaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para manipular Salões.
 */
@Tag(name = "Salões", description = "Gerenciamento de salões de beleza")
@RestController
@RequestMapping("/api/saloes")
public class SalaoController {

    @Autowired
    private SalaoService salaoService;

    /**
     * Lista todos os salões.
     * Qualquer usuário pode acessar.
     */
    @Operation(summary = "Lista todos os salões", description = "Público")
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<SalaoDTO>> listar() {
        List<SalaoDTO> lista = salaoService.listarTodos();
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca um salão por ID.
     * Qualquer usuário pode acessar detalhes.
     */
    @Operation(summary = "Busca salão por ID", description = "Público")
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<SalaoDTO> buscar(@PathVariable Long id) {
        SalaoDTO dto = salaoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Cria um novo salão.
     * Apenas usuário com role DONO_SALAO pode criar.
     */
    @Operation(summary = "Cria um novo salão", description = "Somente DONO_SALAO")
    @PreAuthorize("hasRole('DONO_SALAO')")
    @PostMapping
    public ResponseEntity<SalaoDTO> criar(@Valid @RequestBody SalaoDTO dto) {
        SalaoDTO criado = salaoService.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * Deleta um salão existente.
     * Apenas usuário com role DONO_SALAO pode deletar.
     */
    @Operation(summary = "Deleta um salão", description = "Somente DONO_SALAO")
    @PreAuthorize("hasRole('DONO_SALAO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        salaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
