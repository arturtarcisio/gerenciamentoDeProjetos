package com.github.arturtcs.gerenciamentodeprojetos.service;

import com.github.arturtcs.gerenciamentodeprojetos.model.Projeto;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ProjetoDTO;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ProjetoEmAbertoDTO;

import java.util.List;

public interface ProjetoService {

    boolean verificaSeHaClienteEmAlgumProjeto (Long id);
    ProjetoDTO cadastrarProjeto(ProjetoDTO projetoDTO);
    ProjetoDTO atualizarProjeto(Long id, ProjetoDTO projetoDTO);
    void deletarProjeto(Long id);
    List<ProjetoDTO> listarProjetos();
    ProjetoDTO listarProjetoPorId(Long id);
    List<ProjetoEmAbertoDTO> listarProjetosEmAberto();
}
