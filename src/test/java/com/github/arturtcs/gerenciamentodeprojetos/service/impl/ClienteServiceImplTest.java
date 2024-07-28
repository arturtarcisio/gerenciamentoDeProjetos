package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ClienteComProjetosException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ConflitoAtributoException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ResourceNotFoundException;
import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ClienteDTO;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ClienteRepository;
import com.github.arturtcs.gerenciamentodeprojetos.service.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProjetoService projetoService;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .nome("Cliente Teste")
                .email("cliente@teste.com")
                .cpf("12345678900") // CPF válido com 11 dígitos
                .build();

        clienteDTO = new ClienteDTO(1L, "Cliente Teste", "cliente@teste.com", "12345678900");
    }

    @Test
    void cadastrarCliente_sucesso() {
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        when(clienteRepository.existsByCpf(anyString())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO result = clienteService.cadastrarCliente(clienteDTO);

        assertNotNull(result);
        assertEquals(clienteDTO.email(), result.email());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void cadastrarCliente_emailExistente() {
        when(clienteRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(ConflitoAtributoException.class, () -> clienteService.cadastrarCliente(clienteDTO));
    }

    @Test
    void cadastrarCliente_cpfExistente() {
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        when(clienteRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(ConflitoAtributoException.class, () -> clienteService.cadastrarCliente(clienteDTO));
    }

    @Test
    void deletarCliente_sucesso() {
        when(projetoService.verificaSeHaClienteEmAlgumProjeto(anyLong())).thenReturn(false);
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));

        clienteService.deletarCliente(1L);

        verify(clienteRepository, times(1)).delete(any(Cliente.class));
    }

    @Test
    void deletarCliente_clienteComProjetos() {
        when(projetoService.verificaSeHaClienteEmAlgumProjeto(anyLong())).thenReturn(true);

        assertThrows(ClienteComProjetosException.class, () -> clienteService.deletarCliente(1L));
    }

    @Test
    void deletarCliente_clienteNaoEncontrado() {
        when(projetoService.verificaSeHaClienteEmAlgumProjeto(anyLong())).thenReturn(false);
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.deletarCliente(1L));
    }

    @Test
    void atualizarCliente_sucesso() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO result = clienteService.atualizarCliente(1L, clienteDTO);

        assertNotNull(result);
        assertEquals(clienteDTO.email(), result.email());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void atualizarCliente_clienteNaoEncontrado() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.atualizarCliente(1L, clienteDTO));
    }

    @Test
    void listarClientes_sucesso() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<ClienteDTO> result = clienteService.listarClientes();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void listarClientePorId_sucesso() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));

        ClienteDTO result = clienteService.listarClientePorId(1L);

        assertNotNull(result);
        assertEquals(clienteDTO.email(), result.email());
    }

    @Test
    void listarClientePorId_clienteNaoEncontrado() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.listarClientePorId(1L));
    }
}
