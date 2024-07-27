package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ClienteComProjetosException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ResourceNotFoundException;
import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ClienteDTO;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ClienteRepository;
import com.github.arturtcs.gerenciamentodeprojetos.service.ClienteService;
import com.github.arturtcs.gerenciamentodeprojetos.service.ProjetoService;
import com.github.arturtcs.gerenciamentodeprojetos.util.ValidacoesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProjetoService projetoService;

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
        boolean clienteEstaEmProjeto = projetoService.verificaSeHaClienteEmAlgumProjeto(id);
        if (clienteEstaEmProjeto == true) {
            throw new ClienteComProjetosException("O cliente não pode ser excluído pois está vinculado a um ou mais projetos.");
        } else {
            clienteRepository
                    .findById(id)
                    .map( user -> {
                        clienteRepository.delete(user);
                        return Void.TYPE;
                    })
                    .orElseThrow( () -> new ResourceNotFoundException("Cliente não existe na base de dados para ser deletado.") );
        }
    }

    @Override
    public Cliente atualizarCliente(Long id, Cliente cliente) {

        ValidacoesUtil.validarNome(cliente.getNome());
        ValidacoesUtil.validarCpf(cliente.getCpf());
        ValidacoesUtil.validarEmail(cliente.getEmail());

        return clienteRepository.findById(id).map(clienteRetornado -> {
            cliente.setId(clienteRetornado.getId());
            cliente.setNome(clienteRetornado.getNome());
            cliente.setCpf(clienteRetornado.getCpf());
            cliente.setEmail(clienteRetornado.getEmail());

            return clienteRepository.save(cliente);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente listarClientePorId(Long id) {
        var optionalCliente = clienteRepository.findById(id);
        return optionalCliente.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }
}
