package com.organizo.organizobackend.repository;

import com.organizo.organizobackend.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório JPA para Profissional.
 * Permite buscar todos e por salão.
 */
@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    /**
     * Retorna todos os profissionais que trabalham em um salão.
     * @param salaoId ID do salão
     */
    List<Profissional> findBySalaoId(Long salaoId);
}
