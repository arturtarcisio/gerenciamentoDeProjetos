package com.github.arturtcs.gerenciamentodeprojetos.service;

import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {

    void cadastrarCliente (Cliente cliente);
    void deletarCliente (Long id);
    void atualizarCliente (Long id, Cliente cliente);
    List<Cliente> listarClientes();
    Cliente listarClientePorId(Long id);

}
