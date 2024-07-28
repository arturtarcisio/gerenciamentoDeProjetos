package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusProjeto;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ConflitoAtributoException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.RegraDeNegocioException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ResourceNotFoundException;
import com.github.arturtcs.gerenciamentodeprojetos.model.Atividade;
import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.Projeto;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.AtividadeDTO;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ProjetoDTO;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ProjetoEmAbertoDTO;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ClienteRepository;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ProjetoRepository;
import com.github.arturtcs.gerenciamentodeprojetos.service.ProjetoService;
import com.github.arturtcs.gerenciamentodeprojetos.util.ValidacoesUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
        validarProjetoDTO(projetoDTO);

        Cliente cliente = clienteRepository.findById(projetoDTO.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("O cliente não existe na base de dados"));

        Projeto novoProjeto = Projeto.builder()
                .nome(projetoDTO.nome())
                .status(projetoDTO.status())
                .cliente(cliente)
                .build();

        return converterParaDTO(projetoRepository.save(novoProjeto));
    }

    @Override
    public ProjetoDTO atualizarProjeto(Long id, ProjetoDTO projetoDTO) {
        Projeto projetoExistente = projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));

        validarProjetoDTO(projetoDTO);

        if (!projetoExistente.getNome().equals(projetoDTO.nome()) && projetoRepository.existsByNome(projetoDTO.nome())) {
            throw new ConflitoAtributoException("Nome do projeto já existente");
        }

        Cliente cliente = clienteRepository.findById(projetoDTO.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        projetoExistente.setNome(projetoDTO.nome());
        projetoExistente.setStatus(projetoDTO.status());
        projetoExistente.setCliente(cliente);

        return converterParaDTO(projetoRepository.save(projetoExistente));
    }

    @Transactional
    @Override
    public void deletarProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
        projetoRepository.delete(projeto);
    }

    @Override
    public List<ProjetoDTO> listarProjetos() {
        return projetoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjetoDTO listarProjetoPorId(Long id) {
        return projetoRepository.findById(id)
                .map(this::converterParaDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
    }

    public List<ProjetoEmAbertoDTO> listarProjetosEmAberto() {
        List<Projeto> projetos = projetoRepository.findByStatusWithAtividades(StatusProjeto.ABERTO);
        return projetos.stream().map(this::toProjetoEmAbertoDTO).collect(Collectors.toList());
    }

    private void validarProjetoDTO(ProjetoDTO projetoDTO) {
        ValidacoesUtil.validarNome(projetoDTO.nome());
        ValidacoesUtil.validaStatusDoProjeto(projetoDTO.status());

        if (projetoDTO.clienteId() == null) {
            throw new RegraDeNegocioException("Cliente deve ser informado");
        }
    }

    private ProjetoDTO converterParaDTO(Projeto projeto) {
        return new ProjetoDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getStatus(),
                projeto.getCliente().getId()
        );
    }

    private ProjetoEmAbertoDTO toProjetoEmAbertoDTO(Projeto projeto) {
        return new ProjetoEmAbertoDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getStatus(),
                projeto.getCliente().getNome(),
                toAtividadeDTOSet(projeto.getAtividades())
        );
    }

    private Set<AtividadeDTO> toAtividadeDTOSet(Set<Atividade> atividades) {
        return atividades.stream().map(this::toAtividadeDTO).collect(Collectors.toSet());
    }

    private AtividadeDTO toAtividadeDTO(Atividade atividade) {
        return new AtividadeDTO(
                atividade.getId(),
                atividade.getDescricao(),
                atividade.getStatus(),
                atividade.getProjeto().getId()
        );
    }
}
