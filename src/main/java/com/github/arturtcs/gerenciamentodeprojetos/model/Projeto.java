package com.github.arturtcs.gerenciamentodeprojetos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusProjeto;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL)
    private Set<Atividade> atividades;


}