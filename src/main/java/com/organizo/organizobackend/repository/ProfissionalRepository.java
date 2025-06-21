package com.organizo.organizobackend.repository;

import com.organizo.organizobackend.model.Profissional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



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
    Page<Profissional> findBySalaoId(Long salaoId, Pageable pageable);

    // Listar profissionais de um salão e serviço com paginação
    Page<Profissional> findBySalaoIdAndServicos_Id(
            Long salaoId, Long servicoId, Pageable pageable);
}
