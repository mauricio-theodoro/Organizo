package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.PaginatedResponse;
import com.organizo.organizobackend.dto.SalaoDTO;
import com.organizo.organizobackend.service.SalaoService;
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
 * Endpoints REST para manipular Salões com suporte a paginação.
 */
@Tag(name = "Salões", description = "Gerenciamento de salões de beleza")
@RestController
@RequestMapping("/api/saloes")
public class SalaoController {

    private final SalaoService salaoService;

    @Autowired
    public SalaoController(SalaoService salaoService) {
        this.salaoService = salaoService;
    }

    /**
     * GET /api/saloes?page=0&size=10
     * Retorna uma página de Salões.
     */
    @Operation(summary = "Lista salões paginados", description = "Público")
    @GetMapping
    public ResponseEntity<PaginatedResponse<SalaoDTO>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        // Executa consulta paginada e converte para DTO
        Page<SalaoDTO> page = salaoService.listar(pageable);

        // Constrói resposta padronizada
        PaginatedResponse<SalaoDTO> resp = new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return ResponseEntity.ok(resp);
    }

    /**
     * GET /api/saloes/{id}
     * Qualquer usuário pode acessar detalhes.
     */
    @Operation(summary = "Busca salão por ID", description = "Público")
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<SalaoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(salaoService.buscarPorId(id));
    }

    /**
     * POST /api/saloes
     * Cria um novo salão (role DONO_SALAO).
     */
    @Operation(summary = "Cria um novo salão", description = "Somente DONO_SALAO")
    @PreAuthorize("hasRole('DONO_SALAO')")
    @PostMapping
    public ResponseEntity<SalaoDTO> criar(@Valid @RequestBody SalaoDTO dto) {
        SalaoDTO criado = salaoService.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * DELETE /api/saloes/{id}
     * Deleta um salão (role DONO_SALAO).
     */
    @Operation(summary = "Deleta um salão", description = "Somente DONO_SALAO")
    @PreAuthorize("hasRole('DONO_SALAO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        salaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
