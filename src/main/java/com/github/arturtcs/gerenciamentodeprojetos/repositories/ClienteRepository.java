package com.github.arturtcs.gerenciamentodeprojetos.repositories;

import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
