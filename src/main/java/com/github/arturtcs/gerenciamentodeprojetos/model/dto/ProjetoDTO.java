package com.github.arturtcs.gerenciamentodeprojetos.model.dto;

import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusProjeto;

import java.util.Set;

public record ProjetoDTO(Long id, String nome, StatusProjeto status, Long clienteId) {
}
