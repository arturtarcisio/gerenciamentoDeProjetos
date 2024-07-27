package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ConflitoAtributoException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.RegraDeNegocioException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ResourceNotFoundException;
import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.Projeto;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ProjetoDTO;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.AtividadeRepository;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ClienteRepository;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ProjetoRepository;
import com.github.arturtcs.gerenciamentodeprojetos.service.ProjetoService;
import com.github.arturtcs.gerenciamentodeprojetos.util.ValidacoesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoServiceImpl implements ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean verificaSeHaClienteEmAlgumProjeto(Long id) {
        return projetoRepository.existsByCliente_Id(id);
    }

    @Override
    public ProjetoDTO cadastrarProjeto(ProjetoDTO projetoDTO) {

        ValidacoesUtil.validarNome(projetoDTO.nome());

        // Verifica se o nome do projeto já existe
        if (projetoRepository.existsByNome(projetoDTO.nome())) {
            throw new ConflitoAtributoException("Nome do projeto já existente");
        }

        // Verifica se o cliente foi informado
        if (projetoDTO.clienteId() == null) {
            throw new RegraDeNegocioException("Cliente deve ser informado para cadastro do projeto");
        }

        // Verifica se o cliente existe
        Cliente cliente = clienteRepository.findById(projetoDTO.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("O cliente ao qual você deseja vincular ao projeto não existe na base de dados"));

        // Cria o novo projeto
        Projeto novoProjeto = Projeto.builder()
                .nome(projetoDTO.nome())
                .status(projetoDTO.status())
                .cliente(cliente)
                .build();

        // Salva o projeto no repositório
        Projeto projetoSalvo = projetoRepository.save(novoProjeto);

        // Retorna o DTO do projeto salvo
        return new ProjetoDTO(
                projetoSalvo.getId(),
                projetoSalvo.getNome(),
                projetoSalvo.getStatus(),
                projetoSalvo.getCliente().getId()
        );
    }

    @Override
    public ProjetoDTO atualizarProjeto(Long id, ProjetoDTO projetoDTO) {

        Projeto projetoExistente = projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));

        ValidacoesUtil.validarNome(projetoDTO.nome());

        if (!projetoExistente.getNome().equals(projetoDTO.nome()) && projetoRepository.existsByNome(projetoDTO.nome())) {
            throw new RegraDeNegocioException("Nome do projeto já existente");
        }

        if (projetoDTO.clienteId() != null) {
            Cliente cliente = clienteRepository.findById(projetoDTO.clienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado. Favor informar um cliente que está cadastrado na base de dados."));
            projetoExistente.setCliente(cliente);
        } else {
            throw new RegraDeNegocioException("Cliente deve ser informado.");
        }

        projetoExistente.setNome(projetoDTO.nome());
        projetoExistente.setStatus(projetoDTO.status());

        Projeto projetoAtualizado = projetoRepository.save(projetoExistente);

        return new ProjetoDTO(
                projetoAtualizado.getId(),
                projetoAtualizado.getNome(),
                projetoAtualizado.getStatus(),
                projetoAtualizado.getCliente().getId()
        );
    }

    @Override
    public void deletarProjeto(Long id) {
        projetoRepository.findById(id)
                .map(projeto -> {
                    projetoRepository.delete(projeto);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não existe na base de dados para ser deletado."));
    }

    @Override
    public List<ProjetoDTO> listarProjetos() {
        return projetoRepository.findAll().stream()
                .map(projeto -> new ProjetoDTO(
                        projeto.getId(),
                        projeto.getNome(),
                        projeto.getStatus(),
                        projeto.getCliente().getId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ProjetoDTO listarProjetoPorId(Long id) {
        return projetoRepository.findById(id)
                .map(projeto -> new ProjetoDTO(
                        projeto.getId(),
                        projeto.getNome(),
                        projeto.getStatus(),
                        projeto.getCliente().getId()
                ))
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
    }
}
