package com.organizo.organizobackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Cliente que agenda serviços nos salões.
 */
@Entity
@Table(name = "cliente")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 50)
    private String sobrenome;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefone;

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