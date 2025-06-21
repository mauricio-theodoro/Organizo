package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Profissionais por Serviço",
        description = "Listar profissionais de um serviço em um salão (paginado)")
@RestController
@RequestMapping("/api/saloes/{salaoId}/servicos/{servicoId}/profissionais")
public class SalaoServicoProfissionalController {

    private final ProfissionalService profissionalService;

    @Autowired
    public SalaoServicoProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @Operation(
            summary = "Lista profissionais de um salão e serviço (paginado)",
            description = "Retorna uma página de profissionais habilitados para esse serviço num salão"
    )
    @PreAuthorize("hasAnyRole('DONO_SALAO','PROFISSIONAL','CLIENTE')")
    @GetMapping
    public ResponseEntity<Page<ProfissionalDTO>> listar(
            @PathVariable Long salaoId,
            @PathVariable Long servicoId,
            Pageable pageable) {

        Page<ProfissionalDTO> page = profissionalService
                .listarPorSalaoEServico(salaoId, servicoId, pageable);
        return ResponseEntity.ok(page);
    }
}
