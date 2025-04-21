package com.organizo.organizobackend.repository;

import com.organizo.organizobackend.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    List<Profissional> findBySalaoId(Long salaoId);
}