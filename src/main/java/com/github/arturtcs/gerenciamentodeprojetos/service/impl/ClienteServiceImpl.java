package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ClienteDTO;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ClienteRepository;
import com.github.arturtcs.gerenciamentodeprojetos.service.ClienteService;
import com.github.arturtcs.gerenciamentodeprojetos.util.ValidacoesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public ClienteDTO cadastrarCliente(ClienteDTO clienteDTO) {

        ValidacoesUtil.validarNome(clienteDTO.nome());
        ValidacoesUtil.validarCpf(clienteDTO.cpf());
        ValidacoesUtil.validarEmail(clienteDTO.email());

        Cliente novoCliente = Cliente.builder()
                .cpf(clienteDTO.cpf())
                .email(clienteDTO.email())
                .nome(clienteDTO.nome())
                .build();

        Cliente clienteSalvo = clienteRepository.save(novoCliente);

        return new ClienteDTO(
                clienteSalvo.getId(),
                clienteSalvo.getNome(),
                clienteSalvo.getEmail(),
                clienteSalvo.getCpf()
        );
    }

    @Override
    public void deletarCliente(Long id) {

    }

    @Override
    public void atualizarCliente(Long id, Cliente cliente) {

    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente listarClientePorId(Long id) {
        return null;
    }
}
