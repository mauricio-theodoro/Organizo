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
 * Endpoints para manipular serviços vinculados a um salão.
 */
@Tag(name = "Serviços de Salão", description = "Criação e listagem de serviços por salão")
@RestController
@RequestMapping("/api/saloes/{salaoId}/servicos")
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

    @Operation(summary = "Cria um serviço atrelado a um salão", description = "Somente DONO_SALAO")
    @PreAuthorize("hasRole('DONO_SALAO')")
    @PostMapping
    public ResponseEntity<ServicoDTO> criarServico(
            @PathVariable Long salaoId,
            @Valid @RequestBody ServicoDTO dto) {
        ServicoDTO criado = servicoService.criar(salaoId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @Operation(summary = "Lista serviços de um salão", description = "Público")
    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listarServicosDoSalao(
            @PathVariable Long salaoId) {
        List<ServicoDTO> list = servicoService.listarPorSalao(salaoId);
        return ResponseEntity.ok(list);
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
