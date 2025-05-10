package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.PaginatedResponse;
import com.organizo.organizobackend.dto.ServicoDTO;
import com.organizo.organizobackend.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para manipular Servicos.
 */
@Tag(name = "Serviços", description = "Gestão dos serviços oferecidos pelos salões")
@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    @Autowired
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    /**
     * GET /api/servicos
     * Retorna página de Serviços.
     */
    @Operation(summary = "Lista todos os serviços", description = "Público")
    @GetMapping
    public ResponseEntity<PaginatedResponse<ServicoDTO>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        // obtém o Page<ServicoDTO> do serviço
        Page<ServicoDTO> page = servicoService.listar(pageable);

        // monta nosso DTO paginado
        PaginatedResponse<ServicoDTO> resp = new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return ResponseEntity.ok(resp);
    }

    /**
     * GET /api/servicos/{id}
     * @param id ID do serviço
     * @return serviço encontrado ou 404
     */
    @Operation(summary = "Busca serviço por ID", description = "Público")
    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscarPorId(id));
    }

    // → NOVO POST para criar
    @Operation(summary = "Cria um novo serviço", description = "Somente DONO_SALAO")
    @PreAuthorize("hasRole('DONO_SALAO')")
    @PostMapping
    public ResponseEntity<ServicoDTO> criar(@Valid @RequestBody ServicoDTO dto) {
        ServicoDTO criado = servicoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /** PUT para atualizar */
    @Operation(summary = "Atualiza um serviço", description = "Somente DONO_SALAO")
    @PreAuthorize("hasRole('DONO_SALAO')")
    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ServicoDTO dto) {
        return ResponseEntity.ok(servicoService.atualizar(id, dto));
    }

    /** DELETE para apagar */
    @Operation(summary = "Deleta um serviço", description = "Somente DONO_SALAO")
    @PreAuthorize("hasRole('DONO_SALAO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
