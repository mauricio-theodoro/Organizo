package com.organizo.organizobackend.controller;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.model.Profissional;
import com.organizo.organizobackend.model.Servico;
import com.organizo.organizobackend.repository.ProfissionalRepository;
import com.organizo.organizobackend.repository.ServicoRepository;
import com.organizo.organizobackend.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicos/{servicoId}/profissionais")
public class ServicoProfissionalController {

    @Autowired
    private ServicoRepository servRepo;
    @Autowired private ProfissionalRepository profRepo;


    private final ProfissionalService profissionalService;

    @Autowired
    public ServicoProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }


    @PostMapping("/{profissionalId}")
    @PreAuthorize("hasRole('DONO_SALAO')")
    public ResponseEntity<Void> vincular(
            @PathVariable Long servicoId,
            @PathVariable Long profissionalId) {

        Servico svc = servRepo.findById(servicoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));
        Profissional prof = profRepo.findById(profissionalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));

        svc.getProfissionais().add(prof);
        servRepo.save(svc);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{profissionalId}")
    @PreAuthorize("hasRole('DONO_SALAO')")
    public ResponseEntity<Void> desvincular(
            @PathVariable Long servicoId,
            @PathVariable Long profissionalId) {

        Servico svc = servRepo.findById(servicoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));
        svc.getProfissionais().removeIf(p -> p.getId().equals(profissionalId));
        servRepo.save(svc);
        return ResponseEntity.noContent().build();
    }


}
