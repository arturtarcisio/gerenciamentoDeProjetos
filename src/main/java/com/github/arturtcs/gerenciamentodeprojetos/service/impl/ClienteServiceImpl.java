package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ClienteComProjetosException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ConflitoAtributoException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ResourceNotFoundException;
import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ClienteDTO;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ClienteRepository;
import com.github.arturtcs.gerenciamentodeprojetos.service.ClienteService;
import com.github.arturtcs.gerenciamentodeprojetos.service.ProjetoService;
import com.github.arturtcs.gerenciamentodeprojetos.util.ValidacoesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProjetoService projetoService;

    @Override
    public ClienteDTO cadastrarCliente(ClienteDTO clienteDTO) {
        validarClienteDTO(clienteDTO);

        if (clienteRepository.existsByEmail(clienteDTO.email())) {
            throw new ConflitoAtributoException("Email já existente");
        }

        if (clienteRepository.existsByCpf(clienteDTO.cpf())) {
            throw new ConflitoAtributoException("CPF já existente");
        }

        Cliente novoCliente = Cliente.builder()
                .cpf(clienteDTO.cpf())
                .email(clienteDTO.email())
                .nome(clienteDTO.nome())
                .build();

        return converterParaDTO(clienteRepository.save(novoCliente));
    }

    @Override
    public void deletarCliente(Long id) {
        if (projetoService.verificaSeHaClienteEmAlgumProjeto(id)) {
            throw new ClienteComProjetosException("O cliente não pode ser excluído pois está vinculado a um ou mais projetos.");
        }

        clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado na base de dados para ser deletado."));
    }

    @Override
    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO) {
        validarClienteDTO(clienteDTO);

        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        if (emailOuCpfExistente(clienteDTO, clienteExistente)) {
            throw new ConflitoAtributoException("Email ou CPF já existente");
        }

        clienteExistente.setNome(clienteDTO.nome());
        clienteExistente.setCpf(clienteDTO.cpf());
        clienteExistente.setEmail(clienteDTO.email());

        return converterParaDTO(clienteRepository.save(clienteExistente));
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO listarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .map(this::converterParaDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    private void validarClienteDTO(ClienteDTO clienteDTO) {
        ValidacoesUtil.validarNome(clienteDTO.nome());
        ValidacoesUtil.validarCpf(clienteDTO.cpf());
        ValidacoesUtil.validarEmail(clienteDTO.email());
    }

    private boolean emailOuCpfExistente(ClienteDTO clienteDTO, Cliente clienteExistente) {
        return (clienteRepository.existsByEmail(clienteDTO.email()) && !clienteExistente.getEmail().equals(clienteDTO.email())) ||
                (clienteRepository.existsByCpf(clienteDTO.cpf()) && !clienteExistente.getCpf().equals(clienteDTO.cpf()));
    }

    private ClienteDTO converterParaDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf()
        );
    }
}
