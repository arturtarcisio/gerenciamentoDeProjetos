package com.github.arturtcs.gerenciamentodeprojetos.service;

import com.github.arturtcs.gerenciamentodeprojetos.model.dto.AtividadeDTO;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ProjetoDTO;

import java.util.List;

public interface AtividadeService {

    AtividadeDTO cadastrarAtividade(AtividadeDTO atividadeDTO);
    AtividadeDTO atualizarAtividade(Long id, AtividadeDTO atividadeDTO);
    void deletarAtividade(Long id);
    List<AtividadeDTO> listarAtividades();
    AtividadeDTO listarAtividadePorId(Long id);
}
