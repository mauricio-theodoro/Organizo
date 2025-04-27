package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.MovimentacaoCaixaDTO;
import com.organizo.organizobackend.service.MovimentacaoCaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caixa")
public class MovimentacaoCaixaController {

    @Autowired private MovimentacaoCaixaService service;

    /** Gera movimentação a partir de agendamento concluído */
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/agendamento/{id}")
    public ResponseEntity<MovimentacaoCaixaDTO> gerar(@PathVariable Long id,
                                                      @RequestParam Double porcentagemProfissional) {
        MovimentacaoCaixaDTO dto = service.gerarPorAgendamento(id, porcentagemProfissional);
        return ResponseEntity.status(201).body(dto);
    }

    /** Lista todas as movimentações */
    @PreAuthorize("hasRole('DONO_SALAO')")
    @GetMapping
    public ResponseEntity<List<MovimentacaoCaixaDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }
}