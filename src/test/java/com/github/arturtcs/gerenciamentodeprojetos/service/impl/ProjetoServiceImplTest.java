package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusAtividade;
import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusProjeto;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ResourceNotFoundException;
import com.github.arturtcs.gerenciamentodeprojetos.model.Atividade;
import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.Projeto;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ProjetoDTO;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ProjetoEmAbertoDTO;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ClienteRepository;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceImplTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ProjetoServiceImpl projetoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void verificaSeHaClienteEmAlgumProjeto() {
        when(projetoRepository.existsByCliente_Id(anyLong())).thenReturn(true);

        boolean result = projetoService.verificaSeHaClienteEmAlgumProjeto(1L);

        assertTrue(result);
        verify(projetoRepository, times(1)).existsByCliente_Id(anyLong());
    }

    @Test
    void cadastrarProjeto() {
        ProjetoDTO projetoDTO = new ProjetoDTO(null, "Projeto Teste", StatusProjeto.ABERTO, 1L);

        // Criação de Clientes
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Cliente 1");
        cliente1.setCpf("45236429072");
        cliente1.setEmail("cliente1@email.com");

        // Criação de Projetos
        Projeto projeto1 = new Projeto();
        projeto1.setNome("Projeto Teste");
        projeto1.setStatus(StatusProjeto.ABERTO);
        projeto1.setCliente(cliente1);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente1));
        when(projetoRepository.save(any(Projeto.class))).thenReturn(projeto1);

        ProjetoDTO result = projetoService.cadastrarProjeto(projetoDTO);

        assertNotNull(result);
        assertEquals("Projeto Teste", result.nome());
        verify(clienteRepository, times(1)).findById(anyLong());
        verify(projetoRepository, times(1)).save(any(Projeto.class));
    }

    @Test
    void atualizarProjeto() {
        ProjetoDTO projetoDTO = new ProjetoDTO(null, "Projeto Atualizado", StatusProjeto.CONCLUIDO, 1L);

        // Criação de Clientes
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Cliente 1");
        cliente1.setCpf("45236429072");
        cliente1.setEmail("cliente1@email.com");

        // Criação de Projetos
        Projeto projetoExistente = new Projeto();
        projetoExistente.setNome("Projeto 1");
        projetoExistente.setStatus(StatusProjeto.ABERTO);
        projetoExistente.setCliente(cliente1);

        when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projetoExistente));
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente1));
        when(projetoRepository.save(any(Projeto.class))).thenReturn(projetoExistente);

        ProjetoDTO result = projetoService.atualizarProjeto(1L, projetoDTO);

        assertNotNull(result);
        assertEquals("Projeto Atualizado", result.nome());
        verify(projetoRepository, times(1)).findById(anyLong());
        verify(clienteRepository, times(1)).findById(anyLong());
        verify(projetoRepository, times(1)).save(any(Projeto.class));
    }

    @Test
    void deletarProjeto() {

        Projeto projeto = new Projeto();
        projeto.setNome("Projeto 1");
        projeto.setStatus(StatusProjeto.ABERTO);
        projeto.setCliente(new Cliente());

        when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projeto));

        projetoService.deletarProjeto(1L);

        verify(projetoRepository, times(1)).findById(anyLong());
        verify(projetoRepository, times(1)).delete(any(Projeto.class));
    }

    @Test
    void listarProjetos() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto teste");
        projeto.setStatus(StatusProjeto.ABERTO);
        projeto.setCliente(new Cliente());

        when(projetoRepository.findAll()).thenReturn(Collections.singletonList(projeto));

        List<ProjetoDTO> result = projetoService.listarProjetos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(projetoRepository, times(1)).findAll();
    }

    @Test
    void listarProjetoPorId() {
        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Teste");
        projeto.setStatus(StatusProjeto.ABERTO);
        projeto.setCliente(new Cliente());

        when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projeto));

        ProjetoDTO result = projetoService.listarProjetoPorId(1L);

        assertNotNull(result);
        assertEquals("Projeto Teste", result.nome());
        verify(projetoRepository, times(1)).findById(anyLong());
    }

    @Test
    void listarProjetosEmAberto() {

        Projeto projeto = new Projeto();
        projeto.setNome("Projeto Teste");
        projeto.setStatus(StatusProjeto.ABERTO);
        projeto.setCliente(new Cliente());

        projeto.setAtividades(Set.of(new Atividade(1L, "Atividade Teste", StatusAtividade.PENDENCIA, projeto)));

        when(projetoRepository.findByStatusWithAtividades(StatusProjeto.ABERTO)).thenReturn(Collections.singletonList(projeto));

        List<ProjetoEmAbertoDTO> result = projetoService.listarProjetosEmAberto();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Projeto Teste", result.get(0).nome());
        verify(projetoRepository, times(1)).findByStatusWithAtividades(StatusProjeto.ABERTO);
    }

    // Test cases for exceptions
    @Test
    void cadastrarProjeto_ClienteNaoEncontrado() {
        ProjetoDTO projetoDTO = new ProjetoDTO(null, "Projeto Teste", StatusProjeto.ABERTO, 1L);

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> projetoService.cadastrarProjeto(projetoDTO));
    }

    @Test
    void atualizarProjeto_ProjetoNaoEncontrado() {
        ProjetoDTO projetoDTO = new ProjetoDTO(null, "Projeto Atualizado", StatusProjeto.CONCLUIDO, 1L);

        when(projetoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> projetoService.atualizarProjeto(1L, projetoDTO));
    }

    @Test
    void atualizarProjeto_ClienteNaoEncontrado() {
        ProjetoDTO projetoDTO = new ProjetoDTO(null, "Projeto Atualizado", StatusProjeto.CONCLUIDO, 1L);

        Projeto projetoExistente = new Projeto();
        projetoExistente.setNome("Projeto teste");
        projetoExistente.setStatus(StatusProjeto.ABERTO);
        projetoExistente.setCliente(new Cliente());

        when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projetoExistente));
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> projetoService.atualizarProjeto(1L, projetoDTO));
    }

    @Test
    void atualizarProjeto_ConflitoDeNome() {
        ProjetoDTO projetoDTO = new ProjetoDTO(null, "Projeto Atualizado", StatusProjeto.CONCLUIDO, 1L);

        // Criação de Clientes
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Cliente 1");
        cliente1.setCpf("45236429072");
        cliente1.setEmail("cliente1@email.com");

        // Criação de Projetos
        Projeto projetoExistente = new Projeto();
        projetoExistente.setNome("Projeto 1");
        projetoExistente.setStatus(StatusProjeto.ABERTO);
        projetoExistente.setCliente(cliente1);

        when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projetoExistente));
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> projetoService.atualizarProjeto(1L, projetoDTO));
    }
}
