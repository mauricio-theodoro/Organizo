package com.organizo.organizobackend.repository;

import com.organizo.organizobackend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> { }