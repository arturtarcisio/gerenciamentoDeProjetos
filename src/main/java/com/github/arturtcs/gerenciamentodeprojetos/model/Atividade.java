package com.github.arturtcs.gerenciamentodeprojetos.model;

import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusAtividade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusAtividade status;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

}