package com.github.arturtcs.gerenciamentodeprojetos.service;

import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {

    ClienteDTO cadastrarCliente (ClienteDTO clienteDTO);
    void deletarCliente (Long id);
    void atualizarCliente (Long id, Cliente cliente);
    List<Cliente> listarClientes();
    Cliente listarClientePorId(Long id);

}
