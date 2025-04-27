package com.organizo.organizobackend.repository;

import com.organizo.organizobackend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para a entidade Cliente.
 * Disponibiliza métodos CRUD padrão (findAll, findById, save, deleteById).
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Aqui podemos adicionar consultas customizadas no futuro
}
