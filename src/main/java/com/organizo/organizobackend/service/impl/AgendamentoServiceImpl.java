package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.AgendamentoDTO;
import com.organizo.organizobackend.enums.StatusAgendamento;
import com.organizo.organizobackend.model.Agendamento;
import com.organizo.organizobackend.repository.AgendamentoRepository;
import com.organizo.organizobackend.repository.ClienteRepository;
import com.organizo.organizobackend.repository.ProfissionalRepository;
import com.organizo.organizobackend.repository.ServicoRepository;
import com.organizo.organizobackend.service.AgendamentoService;
import com.organizo.organizobackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da lógica de negócio para Agendamento.
 */
@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    @Autowired
    private AgendamentoRepository agRepo;
    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private ProfissionalRepository profRepo;
    @Autowired
    private ServicoRepository servRepo;
    @Autowired
    private EmailService emailService;

    @Override
    public List<AgendamentoDTO> listarTodos() {
        return agRepo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
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
        // ... criação do agendamento como antes ...
        Agendamento salvo = agRepo.save(ag);

        // NOTIFICAÇÃO por e-mail ao cliente
        String textoCliente = String.format(
                "Olá %s,\n\nSeu agendamento para %s às %s foi criado com sucesso!%n\nObrigado.",
                salvo.getCliente().getNome(),
                salvo.getServico().getNome(),
                salvo.getDataHoraAgendada().toString()
        );
        emailService.sendSimpleMessage(
                salvo.getCliente().getEmail(),
                "Agendamento Confirmado",
                textoCliente
        );

        // NOTIFICAÇÃO ao profissional
        String textoProf = String.format(
                "Olá %s,\n\nVocê tem um novo agendamento para %s no dia %s às %s.%n\nVerifique sua agenda.",
                salvo.getProfissional().getNome(),
                salvo.getServico().getNome(),
                salvo.getDataHoraAgendada().toLocalDate(),
                salvo.getDataHoraAgendada().toLocalTime()
        );
        emailService.sendSimpleMessage(
                salvo.getProfissional().getEmail(),
                "Novo Agendamento",
                textoProf
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
