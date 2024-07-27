package com.github.arturtcs.gerenciamentodeprojetos.resources;

import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ClienteDTO;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ProjetoDTO;
import com.github.arturtcs.gerenciamentodeprojetos.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> listarTodosOsProjetos() {
        return ResponseEntity.ok(projetoService.listarProjetos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> listarProjetoPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(projetoService.listarProjetoPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProjeto(@PathVariable Long id) {
        projetoService.deletarProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoDTO> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoDTO projetoAtualizado) {
        var clienteAtualizado = projetoService.atualizarProjeto(id, projetoAtualizado);
        return ResponseEntity.ok().body(clienteAtualizado);
    }

}
