package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ClienteRepository;
import com.github.arturtcs.gerenciamentodeprojetos.service.ClienteService;
import com.github.arturtcs.gerenciamentodeprojetos.util.ValidacoesUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void cadastrarCliente(Cliente cliente) {

        ValidacoesUtil.validarCpf(cliente.getCpf());
        ValidacoesUtil.validarEmail(cliente.getEmail());

        Cliente novoCliente = Cliente.builder()
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .nome(cliente.getNome())
                .build();

        clienteRepository.save(cliente);
    }

    @Override
    public void deletarCliente(Long id) {

    }

    @Override
    public void atualizarCliente(Long id, Cliente cliente) {

    }

    @Override
    public List<Cliente> listarClientes() {
        return List.of();
    }

    @Override
    public Cliente listarClientePorId(Long id) {
        return null;
    }
}
