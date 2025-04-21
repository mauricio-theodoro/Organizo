package com.organizo.organizobackend.repository;

import com.organizo.organizobackend.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByClienteId(Long clienteId);
    List<Agendamento> findByProfissionalId(Long profissionalId);
}