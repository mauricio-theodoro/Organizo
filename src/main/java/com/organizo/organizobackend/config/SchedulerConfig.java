package com.organizo.organizobackend.config;

import com.organizo.organizobackend.model.Agendamento;
import com.organizo.organizobackend.repository.AgendamentoRepository;
import com.organizo.organizobackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class SchedulerConfig {

    @Autowired private AgendamentoRepository agRepo;
    @Autowired private EmailService emailService;

    /** Executa a cada 15 minutos */
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void enviarLembretes() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime limite = agora.plusHours(2);
        List<Agendamento> proximos = agRepo.findAll().stream()
                .filter(a ->
                        a.getStatus().toString().equals("CONFIRMADO") &&
                                a.getDataHoraAgendada().isAfter(agora) &&
                                a.getDataHoraAgendada().isBefore(limite)
                ).toList();

        for (Agendamento ag : proximos) {
            String texto = String.format(
                    "Olá %s,\n\nEste é um lembrete do seu agendamento de %s hoje às %s.\n\nAtenciosamente.",
                    ag.getCliente().getNome(),
                    ag.getServico().getNome(),
                    ag.getDataHoraAgendada().toLocalTime()
            );
            emailService.sendSimpleMessage(
                    ag.getCliente().getEmail(),
                    "Lembrete de Agendamento",
                    texto
            );
        }
    }
}