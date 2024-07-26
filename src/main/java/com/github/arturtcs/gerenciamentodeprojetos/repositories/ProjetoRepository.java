package com.github.arturtcs.gerenciamentodeprojetos.repositories;

import com.github.arturtcs.gerenciamentodeprojetos.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
