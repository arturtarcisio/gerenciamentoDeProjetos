package com.github.arturtcs.gerenciamentodeprojetos.model;

import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusProjeto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private StatusProjeto status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "projeto")
    private Set<Atividade> atividades;


}