package com.organizo.organizobackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de Cliente
 * sem expor diretamente a entidade JPA.
 */
@Schema(name = "Cliente", description = "Dados de um cliente")
public class ClienteDTO {

    @Schema(description = "ID do cliente", example = "10")
    private Long id;

    @Schema(description = "Nome do cliente", example = "Maria")
    @NotBlank(message = "nome é obrigatório")
    @Size(max = 50, message = "nome não pode exceder 50 caracteres")
    private String nome;

    @Schema(description = "Sobrenome do cliente", example = "Silva")
    @NotBlank(message = "sobrenome é obrigatório")
    @Size(max = 50, message = "sobrenome não pode exceder 50 caracteres")
    private String sobrenome;

    @Schema(description = "E-mail de contato", example = "maria@ex.com")
    @NotBlank(message = "email é obrigatório")
    @Email(message = "email deve ser válido")
    private String email;

    @Schema(description = "Telefone de contato", example = "31988887777")
    @Size(max = 20, message = "telefone não pode exceder 20 caracteres")
    private String telefone;

    @Schema(description = "Data de criação do registro")
    private LocalDateTime criadoEm;

    @Schema(description = "Data de última atualização do registro")
    private LocalDateTime atualizadoEm;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}
