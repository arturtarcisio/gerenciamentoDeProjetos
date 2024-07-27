package com.github.arturtcs.gerenciamentodeprojetos.resources;

import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ClienteDTO;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ProjetoDTO;
import com.github.arturtcs.gerenciamentodeprojetos.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/projetos")
public class ProjetoResource {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    public ResponseEntity<ProjetoDTO> cadastrarProjeto(@RequestBody ProjetoDTO projetoDTO) {
        projetoDTO = projetoService.cadastrarProjeto(projetoDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(projetoDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(projetoDTO);
    }

}
