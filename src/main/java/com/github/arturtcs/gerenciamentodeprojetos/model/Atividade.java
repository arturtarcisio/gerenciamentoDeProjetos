package com.github.arturtcs.gerenciamentodeprojetos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private String status; // Por exemplo: "Pendência", "Em andamento", "Concluída"

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

}