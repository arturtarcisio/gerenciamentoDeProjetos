package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.model.dto.AtividadeDTO;
import com.github.arturtcs.gerenciamentodeprojetos.service.AtividadeService;

import java.util.List;

public class AtividadeServiceImpl implements AtividadeService {

    @Override
    public AtividadeDTO cadastrarAtividade(AtividadeDTO atividadeDTO) {
        return null;
    }

    @Override
    public AtividadeDTO atualizarAtividade(Long id, AtividadeDTO atividadeDTO) {
        return null;
    }

    @Override
    public void deletarAtividade(Long id) {

    }

    @Override
    public List<AtividadeDTO> listarAtividades() {
        return List.of();
    }

    @Override
    public AtividadeDTO listarAtividadePorId(Long id) {
        return null;
    }
}
