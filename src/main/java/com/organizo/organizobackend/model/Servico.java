package com.organizo.organizobackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Define um tipo de serviço oferecido (ex: corte, unhas, depilação).
 */
@Entity
@Table(name = "servico")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Lob
    private String descricao;      // Descrição detalhada

    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos; // Duração em minutos

    @Column(nullable = false)
    private BigDecimal preco;       // Valor do serviço

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