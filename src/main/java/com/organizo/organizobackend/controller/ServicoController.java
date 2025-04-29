package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.ServicoDTO;
import com.organizo.organizobackend.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
     * Retorna página de serviços.
     */
    @Operation(summary = "Lista serviços paginados", description = "Parâmetros: page, size, sort")
    @GetMapping
    public ResponseEntity<Page<ServicoDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(servicoService.listar(pageable));
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
}
