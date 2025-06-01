package com.organizo.organizobackend.service;

import com.organizo.organizobackend.model.Profissional;
import com.organizo.organizobackend.model.Salao;
import com.organizo.organizobackend.model.Usuario;
import com.organizo.organizobackend.repository.ProfissionalRepository;
import com.organizo.organizobackend.repository.SalaoRepository;
import com.organizo.organizobackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Serviço auxiliar para verificações de segurança,
 * especificamente para checar a posse de recursos.
 * Usado em expressões @PreAuthorize.
 */
@Service("salaoSecurityService") // Nome do bean para ser referenciado no @PreAuthorize
public class SalaoSecurityService {

    @Autowired
    private SalaoRepository salaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    /**
     * Verifica se o usuário logado (identificado pelo username/email)
     * é o proprietário (owner) do salão especificado.
     *
     * @param salaoId ID do salão a ser verificado.
     * @param username Email do usuário logado (obtido via principal.username).
     * @return true se o usuário for o dono do salão, false caso contrário.
     */
    @Transactional(readOnly = true)
    public boolean isOwner(Long salaoId, String username) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(username);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        Usuario usuarioLogado = usuarioOpt.get();

        Optional<Salao> salaoOpt = salaoRepository.findById(salaoId);
        if (salaoOpt.isEmpty()) {
            return false;
        }
        Salao salao = salaoOpt.get();

        return salao.getOwner() != null && salao.getOwner().getId().equals(usuarioLogado.getId());
    }

    /**
     * Verifica se o usuário logado (identificado pelo username/email)
     * é o proprietário (owner) do salão onde o profissional especificado trabalha.
     *
     * @param profissionalId ID do profissional a ser verificado.
     * @param username Email do usuário logado.
     * @return true se o usuário for o dono do salão do profissional, false caso contrário.
     */
    @Transactional(readOnly = true)
    public boolean isOwnerOfSalaoContainingProfissional(Long profissionalId, String username) {
        // Busca o profissional
        Optional<Profissional> profissionalOpt = profissionalRepository.findById(profissionalId);
        if (profissionalOpt.isEmpty()) {
            return false; // Profissional não existe
        }
        Profissional profissional = profissionalOpt.get();

        // Verifica se o profissional está vinculado a um salão
        if (profissional.getSalao() == null || profissional.getSalao().getId() == null) {
            return false; // Profissional não pertence a nenhum salão
        }

        // Reutiliza a lógica de isOwner para o salão do profissional
        return isOwner(profissional.getSalao().getId(), username);
    }

    // TODO: Adicionar método isOwnerOfSalaoContainingAgendamento(Long agendamentoId, String username)
    // para proteger endpoints de agendamento que o DONO_SALAO pode acessar.
}

