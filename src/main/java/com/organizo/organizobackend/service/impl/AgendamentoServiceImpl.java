// src/main/java/com/organizo/organizobackend/service/impl/AgendamentoServiceImpl.java
package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.AgendamentoDTO;
import com.organizo.organizobackend.enums.StatusAgendamento;
import com.organizo.organizobackend.model.Agendamento;
import com.organizo.organizobackend.model.Cliente;
import com.organizo.organizobackend.model.Profissional;
import com.organizo.organizobackend.model.Servico;
import com.organizo.organizobackend.repository.AgendamentoRepository;
import com.organizo.organizobackend.repository.ClienteRepository;
import com.organizo.organizobackend.repository.ProfissionalRepository;
import com.organizo.organizobackend.repository.ServicoRepository;
import com.organizo.organizobackend.service.AgendamentoService;
import com.organizo.organizobackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da lógica de negócio para Agendamento,
 * incluindo paginação e envio de e‑mail.
 */
@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    @Autowired private AgendamentoRepository agRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private ProfissionalRepository profRepo;
    @Autowired private ServicoRepository servRepo;
    @Autowired private EmailService emailService;

    @Override
    public Page<AgendamentoDTO> listar(Pageable pageable) {
        // -> Usa JPA para paginação e converte cada entidade em DTO
        return agRepo.findAll(pageable)
                .map(this::toDTO);
    }

    @Override
    public List<AgendamentoDTO> listarPorCliente(Long clienteId) {
        return agRepo.findByClienteId(clienteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgendamentoDTO> listarPorProfissional(Long profissionalId) {
        return agRepo.findByProfissionalId(profissionalId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AgendamentoDTO criar(AgendamentoDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Profissional prof = profRepo.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        Servico serv = servRepo.findById(dto.getServicoId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Agendamento ag = new Agendamento();
        ag.setCliente(cliente);
        ag.setProfissional(prof);
        ag.setServico(serv);
        ag.setDataHoraAgendada(dto.getDataHoraAgendada());
        ag.setStatus(StatusAgendamento.PENDENTE);

        Agendamento salvo = agRepo.save(ag);

        // dispara e‑mails de confirmação
        emailService.sendSimpleMessage(
                cliente.getEmail(),
                "Agendamento Criado",
                String.format("Olá %s, seu agendamento para %s às %s foi criado.",
                        cliente.getNome(), serv.getNome(), ag.getDataHoraAgendada())
        );
        emailService.sendSimpleMessage(
                prof.getEmail(),
                "Novo Agendamento",
                String.format("Olá %s, você tem um agendamento para %s em %s.",
                        prof.getNome(), serv.getNome(), ag.getDataHoraAgendada())
        );

        return toDTO(salvo);
    }

    @Override
    public AgendamentoDTO confirmar(Long id) {
        Agendamento ag = agRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        ag.setStatus(StatusAgendamento.CONFIRMADO);
        return toDTO(agRepo.save(ag));
    }

    @Override
    public AgendamentoDTO cancelar(Long id) {
        Agendamento ag = agRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        ag.setStatus(StatusAgendamento.CANCELADO);
        return toDTO(agRepo.save(ag));
    }

    /**
     * Converte entidade em DTO simples.
     */
    private AgendamentoDTO toDTO(Agendamento ag) {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(ag.getId());
        dto.setClienteId(ag.getCliente().getId());
        dto.setProfissionalId(ag.getProfissional().getId());
        dto.setServicoId(ag.getServico().getId());
        dto.setDataHoraAgendada(ag.getDataHoraAgendada());
        dto.setStatus(ag.getStatus());
        return dto;
    }
}
