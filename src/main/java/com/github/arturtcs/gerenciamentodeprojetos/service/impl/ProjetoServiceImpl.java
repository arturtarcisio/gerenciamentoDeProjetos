package com.github.arturtcs.gerenciamentodeprojetos.service.impl;

import com.github.arturtcs.gerenciamentodeprojetos.repositories.ProjetoRepository;
import com.github.arturtcs.gerenciamentodeprojetos.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjetoServiceImpl implements ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Override
    public boolean verificaSeHaClienteEmAlgumProjeto(Long id) {
        return projetoRepository.existsByCliente_Id(id);
    }
}
