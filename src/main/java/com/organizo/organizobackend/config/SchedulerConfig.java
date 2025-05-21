
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

    private final AgendamentoRepository agRepo;
    private final EmailService emailService;

    @Autowired
    public SchedulerConfig(AgendamentoRepository agRepo,
                           EmailService emailService) {
        this.agRepo        = agRepo;
        this.emailService  = emailService;
    }

    /** Executa a cada 15 minutos */
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void enviarLembretes() {
        LocalDateTime agora  = LocalDateTime.now();
        LocalDateTime limite = agora.plusHours(2);

        List<Agendamento> proximos = agRepo.findConfirmedBetween(agora, limite);
        for (Agendamento ag : proximos) {
            try {
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
            } catch (Exception ex) {
                // Só loga o erro e continua
                System.err.printf(
                        "Falha ao enviar lembrete para Agendamento[id=%d]: %s%n",
                        ag.getId(), ex.getMessage()
                );
            }
        }
    }
}
