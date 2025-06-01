package com.organizo.organizobackend.service.impl;

import com.organizo.organizobackend.dto.AgendamentoDTO;
import com.organizo.organizobackend.enums.StatusAgendamento;
import com.organizo.organizobackend.exception.BusinessException;
import com.organizo.organizobackend.exception.ResourceNotFoundException;
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

import java.time.LocalDateTime;

/**
 * Implementação da lógica de negócio para Agendamento,
 * incluindo paginação, envio de e‑mail e tratamento de erros customizado.
 */
@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    @Autowired private AgendamentoRepository agRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private ProfissionalRepository profRepo;
    @Autowired private ServicoRepository servRepo;
    @Autowired private EmailService emailService;

    /**
     * Retorna página de todos os agendamentos.
     */
    @Override
    public Page<AgendamentoDTO> listar(Pageable pageable) {
        return agRepo.findAll(pageable)
                .map(this::toDTO);
    }

    /**
     * Retorna página de agendamentos filtrados por cliente.
     */
    @Override
    public Page<AgendamentoDTO> listarPorCliente(Long clienteId, Pageable pageable) {
        // Verifica se o cliente existe
        if (!clienteRepo.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente", "ID", clienteId);
        }
        return agRepo.findByClienteId(clienteId, pageable)
                .map(this::toDTO);
    }

    /**
     * Retorna página de agendamentos filtrados por profissional.
     */
    @Override
    public Page<AgendamentoDTO> listarPorProfissional(Long profissionalId, Pageable pageable) {
        // Verifica se o profissional existe
        if (!profRepo.existsById(profissionalId)) {
            throw new ResourceNotFoundException("Profissional", "ID", profissionalId);
        }
        return agRepo.findByProfissionalId(profissionalId, pageable)
                .map(this::toDTO);
    }

    @Override
    public AgendamentoDTO criar(AgendamentoDTO dto) {
        // Valida se a data/hora do agendamento é futura
        if (dto.getDataHoraAgendada() == null || dto.getDataHoraAgendada().isBefore(LocalDateTime.now())) {
            throw new BusinessException("A data e hora do agendamento devem ser futuras.");
        }

        // Busca entidades relacionadas, lançando exceção específica se não encontradas
        Cliente cliente = clienteRepo.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "ID", dto.getClienteId()));
        Profissional prof = profRepo.findById(dto.getProfissionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Profissional", "ID", dto.getProfissionalId()));
        Servico serv = servRepo.findById(dto.getServicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Serviço", "ID", dto.getServicoId()));

        // TODO: Adicionar validação de conflito de horário para o profissional
        // (Ex: verificar se já existe agendamento para o mesmo profissional no mesmo horário)

        Agendamento ag = new Agendamento();
        ag.setCliente(cliente);
        ag.setProfissional(prof);
        ag.setServico(serv);
        ag.setDataHoraAgendada(dto.getDataHoraAgendada());
        ag.setStatus(StatusAgendamento.PENDENTE); // Status inicial

        Agendamento salvo = agRepo.save(ag);

        // Envio de e‑mail ao cliente e profissional (considerar tornar assíncrono)
        try {
            emailService.sendSimpleMessage(
                    cliente.getEmail(),
                    "Organizo - Agendamento Criado",
                    String.format("Olá %s, seu agendamento para '%s' com %s no dia %s às %s foi criado com sucesso e está pendente de confirmação.",
                            cliente.getNome(), serv.getNome(), prof.getNome(),
                            ag.getDataHoraAgendada().toLocalDate(), ag.getDataHoraAgendada().toLocalTime())
            );
            emailService.sendSimpleMessage(
                    prof.getEmail(),
                    "Organizo - Novo Agendamento Recebido",
                    String.format("Olá %s, você recebeu um novo agendamento de %s para o serviço '%s' no dia %s às %s.",
                            prof.getNome(), cliente.getNome(), serv.getNome(),
                            ag.getDataHoraAgendada().toLocalDate(), ag.getDataHoraAgendada().toLocalTime())
            );
        } catch (Exception e) {
            // Logar erro de envio de email, mas não impedir a criação do agendamento
            // logger.error("Falha ao enviar email de confirmação para agendamento {}: {}", salvo.getId(), e.getMessage());
        }

        return toDTO(salvo);
    }

    @Override
    public AgendamentoDTO confirmar(Long id) {
        Agendamento ag = agRepo.findById(id)
                // Lança exceção específica se não encontrar
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento", "ID", id));

        // Validação de regra de negócio: só pode confirmar se estiver PENDENTE
        if (ag.getStatus() != StatusAgendamento.PENDENTE) {
            throw new BusinessException("Agendamento não pode ser confirmado pois não está com status PENDENTE (Status atual: " + ag.getStatus() + ")");
        }

        ag.setStatus(StatusAgendamento.CONFIRMADO);
        Agendamento salvo = agRepo.save(ag);

        // TODO: Enviar email de confirmação

        return toDTO(salvo);
    }

    @Override
    public AgendamentoDTO cancelar(Long id) {
        Agendamento ag = agRepo.findById(id)
                // Lança exceção específica se não encontrar
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento", "ID", id));

        // Validação de regra de negócio: só pode cancelar se estiver PENDENTE ou CONFIRMADO
        if (ag.getStatus() == StatusAgendamento.CANCELADO || ag.getStatus() == StatusAgendamento.CONCLUIDO) {
            throw new BusinessException("Agendamento não pode ser cancelado pois seu status é " + ag.getStatus());
        }

        // TODO: Adicionar regra de antecedência mínima para cancelamento?

        ag.setStatus(StatusAgendamento.CANCELADO);
        Agendamento salvo = agRepo.save(ag);

        // TODO: Enviar email de cancelamento para cliente e profissional

        return toDTO(salvo);
    }

    /**
     * Converte entidade em DTO para resposta.
     */
    private AgendamentoDTO toDTO(Agendamento ag) {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(ag.getId());
        dto.setClienteId(ag.getCliente().getId());
        dto.setProfissionalId(ag.getProfissional().getId());
        dto.setServicoId(ag.getServico().getId());
        dto.setDataHoraAgendada(ag.getDataHoraAgendada());
        dto.setStatus(ag.getStatus());
        // Adicionar nomes para facilitar o frontend (opcional)
        dto.setNomeCliente(ag.getCliente().getNome());
        dto.setNomeProfissional(ag.getProfissional().getNome());
        dto.setNomeServico(ag.getServico().getNome());
        return dto;
    }
}
