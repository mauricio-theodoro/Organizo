package com.organizo.organizobackend.exception;

/**
 * Exceção lançada quando uma entidade não é encontrada.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}