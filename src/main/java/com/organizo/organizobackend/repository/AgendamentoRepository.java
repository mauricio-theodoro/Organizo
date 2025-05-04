package com.organizo.organizobackend.repository;

import com.organizo.organizobackend.model.Agendamento;
import com.organizo.organizobackend.model.Profissional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    /**
     * Paginação geral de agendamentos.
     */
    Page<Agendamento> findAll(Pageable pageable);

    /**
     * Paginação de agendamentos por cliente.
     */
    Page<Agendamento> findByClienteId(Long clienteId, Pageable pageable);

    /**
     * Paginação de agendamentos por profissional.
     */
    Page<Agendamento> findByProfissionalId(Long profissionalId, Pageable pageable);

    boolean existsByProfissionalAndDataHoraAgendada(Profissional p, LocalDateTime dt);
}