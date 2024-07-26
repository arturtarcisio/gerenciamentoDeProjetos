package com.github.arturtcs.gerenciamentodeprojetos.repositories;

import com.github.arturtcs.gerenciamentodeprojetos.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
}
