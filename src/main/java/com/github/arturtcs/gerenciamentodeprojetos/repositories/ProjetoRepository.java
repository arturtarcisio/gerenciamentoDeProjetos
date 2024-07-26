package com.github.arturtcs.gerenciamentodeprojetos.repositories;

import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    boolean existsByCliente_Id(Long id);
}
