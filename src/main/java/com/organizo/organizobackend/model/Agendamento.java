package com.organizo.organizobackend.model;

import com.organizo.organizobackend.enums.StatusAgendamento;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Representa um agendamento de serviço feito pelo cliente.
 */
@Entity
@Table(name = "agendamento")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Agendamento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;            // Quem agendou

    @ManyToOne @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;  // Quem irá atender

    @ManyToOne @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;            // Qual serviço

    @Column(name = "data_hora_agendada", nullable = false)
    private LocalDateTime dataHoraAgendada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;   // Ex: PENDENTE, CONFIRMADO

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist protected void prePersist() {
        criadoEm = LocalDateTime.now(); atualizadoEm = criadoEm;
    }
    @PreUpdate  protected void preUpdate()  {
        atualizadoEm = LocalDateTime.now();
    }
}