package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusAtividade;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.RegraDeNegocioException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ResourceNotFoundException;
import com.github.arturtcs.gerenciamentodeprojetos.model.Atividade;
import com.github.arturtcs.gerenciamentodeprojetos.model.Projeto;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.AtividadeDTO;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.AtividadeRepository;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtividadeServiceImplTest {

    @Mock
    private AtividadeRepository atividadeRepository;

    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private AtividadeServiceImpl atividadeService;

    private Atividade atividade;
    private AtividadeDTO atividadeDTO;
    private Projeto projeto;

    @BeforeEach
    void setUp() {
        projeto = Projeto.builder()
                .id(1L)
                .nome("Projeto Teste")
                .build();

        atividade = Atividade.builder()
                .id(1L)
                .descricao("Atividade Teste")
                .status(StatusAtividade.PENDENCIA)
                .projeto(projeto)
                .build();

        atividadeDTO = new AtividadeDTO(1L, "Atividade Teste", StatusAtividade.PENDENCIA, 1L);
    }

    @Test
    void cadastrarAtividade_sucesso() {
        when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projeto));
        when(atividadeRepository.save(any(Atividade.class))).thenReturn(atividade);

        AtividadeDTO result = atividadeService.cadastrarAtividade(atividadeDTO);

        assertNotNull(result);
        assertEquals(atividadeDTO.descricao(), result.descricao());
        verify(atividadeRepository, times(1)).save(any(Atividade.class));
    }

    @Test
    void cadastrarAtividade_projetoNaoEncontrado() {
        when(projetoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> atividadeService.cadastrarAtividade(atividadeDTO));
    }

    @Test
    void atualizarAtividade_sucesso() {
        when(atividadeRepository.findById(anyLong())).thenReturn(Optional.of(atividade));
        when(projetoRepository.findById(anyLong())).thenReturn(Optional.of(projeto));
        when(atividadeRepository.save(any(Atividade.class))).thenReturn(atividade);

        AtividadeDTO result = atividadeService.atualizarAtividade(1L, atividadeDTO);

        assertNotNull(result);
        assertEquals(atividadeDTO.descricao(), result.descricao());
        verify(atividadeRepository, times(1)).save(any(Atividade.class));
    }

    @Test
    void atualizarAtividade_atividadeNaoEncontrada() {
        when(atividadeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atividadeService.atualizarAtividade(1L, atividadeDTO));
    }

    @Test
    void atualizarAtividade_projetoNaoEncontrado() {
        when(atividadeRepository.findById(anyLong())).thenReturn(Optional.of(atividade));
        when(projetoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> atividadeService.atualizarAtividade(1L, atividadeDTO));
    }

    @Test
    void deletarAtividade_sucesso() {
        when(atividadeRepository.findById(anyLong())).thenReturn(Optional.of(atividade));

        atividadeService.deletarAtividade(1L);

        verify(atividadeRepository, times(1)).delete(any(Atividade.class));
    }

    @Test
    void deletarAtividade_naoEncontrada() {
        when(atividadeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atividadeService.deletarAtividade(1L));
    }

    @Test
    void listarAtividades_sucesso() {
        when(atividadeRepository.findAll()).thenReturn(List.of(atividade));

        List<AtividadeDTO> result = atividadeService.listarAtividades();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void listarAtividadePorId_sucesso() {
        when(atividadeRepository.findById(anyLong())).thenReturn(Optional.of(atividade));

        AtividadeDTO result = atividadeService.listarAtividadePorId(1L);

        assertNotNull(result);
        assertEquals(atividadeDTO.descricao(), result.descricao());
    }

    @Test
    void listarAtividadePorId_naoEncontrada() {
        when(atividadeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atividadeService.listarAtividadePorId(1L));
    }
}
