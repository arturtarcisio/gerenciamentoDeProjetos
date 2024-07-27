package com.github.arturtcs.gerenciamentodeprojetos.model.dto;

import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusAtividade;
import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusProjeto;
import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;

import java.util.Set;

public record AtividadeDTO(Long id, String descricao, StatusAtividade status, Long projetoId) {
}
