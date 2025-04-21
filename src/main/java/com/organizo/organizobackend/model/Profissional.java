package com.organizo.organizobackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Profissional que trabalha em um salão e oferece serviços.
 */
@Entity
@Table(name = "profissional")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 50)
    private String sobrenome;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salao_id", nullable = false)
    private Salao salao;           // FK para o salão onde trabalha

    @ManyToMany
    @JoinTable(name = "profissional_servico",
            joinColumns = @JoinColumn(name = "profissional_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id"))
    private Set<Servico> servicos;  // Serviços que este profissional oferece

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