package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.ServicoDTO;
import com.organizo.organizobackend.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para manipular Servicos.
 */
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
     * @return lista de todos os serviços
     */
    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listar() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }

    /**
     * GET /api/servicos/{id}
     * @param id ID do serviço
     * @return serviço encontrado ou 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(servicoService.buscarPorId(id));
    }
}
