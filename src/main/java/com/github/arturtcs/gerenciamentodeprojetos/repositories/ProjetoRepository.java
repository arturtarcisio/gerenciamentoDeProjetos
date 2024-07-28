package com.github.arturtcs.gerenciamentodeprojetos.repositories;

import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusProjeto;
import com.github.arturtcs.gerenciamentodeprojetos.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    boolean existsByCliente_Id(Long id);
    boolean existsByNome(String nome);

    @Query("SELECT DISTINCT p FROM Projeto p LEFT JOIN FETCH p.atividades WHERE p.status = :status")
    List<Projeto> findByStatusWithAtividades(@Param("status") StatusProjeto status);
}
