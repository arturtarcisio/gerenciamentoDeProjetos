package com.github.arturtcs.gerenciamentodeprojetos.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String cpf;

    @OneToMany(mappedBy = "cliente")
    private Set<Projeto> projetos;


}