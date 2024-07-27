package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.exceptions.RegraDeNegocioException;
import com.github.arturtcs.gerenciamentodeprojetos.exceptions.ResourceNotFoundException;
import com.github.arturtcs.gerenciamentodeprojetos.model.Atividade;
import com.github.arturtcs.gerenciamentodeprojetos.model.Projeto;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.AtividadeDTO;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.AtividadeRepository;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ProjetoRepository;
import com.github.arturtcs.gerenciamentodeprojetos.service.AtividadeService;
import com.github.arturtcs.gerenciamentodeprojetos.util.ValidacoesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeServiceImpl implements AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Override
    public AtividadeDTO cadastrarAtividade(AtividadeDTO atividadeDTO) {
        validarAtividadeDTO(atividadeDTO);

        Projeto projeto = projetoRepository.findById(atividadeDTO.projetoId())
                .orElseThrow(() -> new RegraDeNegocioException("Projeto não encontrado. Favor informar um projeto válido."));

        Atividade novaAtividade = criarNovaAtividade(atividadeDTO, projeto);

        Atividade atividadeSalva = atividadeRepository.save(novaAtividade);

        return converterParaDTO(atividadeSalva);
    }

    @Override
    public AtividadeDTO atualizarAtividade(Long id, AtividadeDTO atividadeDTO) {
        Atividade atividadeExistente = atividadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada. Favor informar uma atividade (ID) válido."));

        validarAtividadeDTO(atividadeDTO);

        Projeto projeto = projetoRepository.findById(atividadeDTO.projetoId())
                .orElseThrow(() -> new RegraDeNegocioException("Projeto não encontrado. Favor informar um projeto válido."));

        atualizarCamposAtividade(atividadeExistente, atividadeDTO, projeto);

        Atividade atividadeSalva = atividadeRepository.save(atividadeExistente);

        return converterParaDTO(atividadeSalva);
    }

    @Override
    public void deletarAtividade(Long id) {
        atividadeRepository.findById(id)
                .map(atividade -> {
                    atividadeRepository.delete(atividade);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não existe na base de dados para ser deletado."));
    }

    @Override
    public List<AtividadeDTO> listarAtividades() {
        return atividadeRepository.findAll().stream()
                .map(atividade -> new AtividadeDTO(
                        atividade.getId(),
                        atividade.getDescricao(),
                        atividade.getStatus(),
                        atividade.getProjeto().getId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public AtividadeDTO listarAtividadePorId(Long id) {
        return atividadeRepository.findById(id)
                .map(atividade -> new AtividadeDTO(
                        atividade.getId(),
                        atividade.getDescricao(),
                        atividade.getStatus(),
                        atividade.getProjeto().getId()
                ))
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada"));
    }

    private void validarAtividadeDTO(AtividadeDTO atividadeDTO) {
        if (atividadeDTO.projetoId() == null) {
            throw new RegraDeNegocioException("Atividade deve conter um projeto. Favor informar qual projeto que você deseja vincular esta atividade.");
        }
        ValidacoesUtil.validarNome(atividadeDTO.descricao());
        ValidacoesUtil.validaStatusDaAtividade(atividadeDTO.status());
    }

    private Atividade criarNovaAtividade(AtividadeDTO atividadeDTO, Projeto projeto) {
        return Atividade.builder()
                .descricao(atividadeDTO.descricao())
                .status(atividadeDTO.status())
                .projeto(projeto)
                .build();
    }

    private AtividadeDTO converterParaDTO(Atividade atividade) {
        return new AtividadeDTO(
                atividade.getId(),
                atividade.getDescricao(),
                atividade.getStatus(),
                atividade.getProjeto().getId()
        );
    }

    private void atualizarCamposAtividade(Atividade atividadeExistente, AtividadeDTO atividadeDTO, Projeto projeto) {
        atividadeExistente.setDescricao(atividadeDTO.descricao());
        atividadeExistente.setStatus(atividadeDTO.status());
        atividadeExistente.setProjeto(projeto);
    }
}
