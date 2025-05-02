package com.organizo.organizobackend.service;

import com.organizo.organizobackend.dto.ClienteDTO;
import com.organizo.organizobackend.mapper.ClienteMapper;
import com.organizo.organizobackend.model.Cliente;
import com.organizo.organizobackend.repository.ClienteRepository;
import com.organizo.organizobackend.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repo;

    @Mock
    private ClienteMapper mapper;  // agora mockamos também o mapper

    @InjectMocks
    private ClienteServiceImpl service;

    @Test
    void criar_devePersistirERetornarDTO() {
        // 1) preparamos a entidade que o mapper irá gerar
        Cliente entidade = new Cliente();
        entidade.setNome("João");
        entidade.setSobrenome("Silva");
        entidade.setEmail("joao@ex.com");
        entidade.setTelefone("31999990000");
        // simula o banco atribuindo ID e timestamps
        Cliente salvo = new Cliente();
        salvo.setId(1L);
        salvo.setNome(entidade.getNome());
        salvo.setSobrenome(entidade.getSobrenome());
        salvo.setEmail(entidade.getEmail());
        salvo.setTelefone(entidade.getTelefone());

        // 2) preparamos o DTO de entrada
        ClienteDTO dtoIn = new ClienteDTO();
        dtoIn.setNome("João");
        dtoIn.setSobrenome("Silva");
        dtoIn.setEmail("joao@ex.com");
        dtoIn.setTelefone("31999990000");

        // 3) stub do mapper.toEntity para devolver nossa 'entidade'
        when(mapper.toEntity(any(ClienteDTO.class))).thenReturn(entidade);

        // 4) stub do repo.save para devolver a 'salvo'
        when(repo.save(entidade)).thenReturn(salvo);

        // 5) stub do mapper.toDto para devolver um DTO com ID
        ClienteDTO dtoOut = new ClienteDTO();
        dtoOut.setId(1L);
        dtoOut.setNome("João");
        dtoOut.setSobrenome("Silva");
        dtoOut.setEmail("joao@ex.com");
        dtoOut.setTelefone("31999990000");
        when(mapper.toDto(salvo)).thenReturn(dtoOut);

        // execução
        ClienteDTO resp = service.criar(dtoIn);

        // validações
        assertEquals(1L, resp.getId());
        assertEquals("João", resp.getNome());
        verify(mapper).toEntity(argThat(new ArgumentMatcher<ClienteDTO>() {
            @Override
            public boolean matches(ClienteDTO c) {
                return "João".equals(c.getNome());
            }
        }));
        verify(repo).save(entidade);
        verify(mapper).toDto(salvo);
    }

    @Test
    void buscarPorId_quandoNaoExiste_deveLancar() {
        when(repo.findById(42L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.buscarPorId(42L));
    }
}
