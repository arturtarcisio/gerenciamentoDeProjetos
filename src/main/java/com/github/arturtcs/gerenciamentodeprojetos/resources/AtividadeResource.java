package com.github.arturtcs.gerenciamentodeprojetos.resources;

import com.github.arturtcs.gerenciamentodeprojetos.model.dto.AtividadeDTO;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ClienteDTO;
import com.github.arturtcs.gerenciamentodeprojetos.service.AtividadeService;
import com.github.arturtcs.gerenciamentodeprojetos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/atividades")
public class AtividadeResource {

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public ResponseEntity<AtividadeDTO> cadastrarAtividade(@RequestBody AtividadeDTO atividadeDTO) {
        atividadeDTO = atividadeService.cadastrarAtividade(atividadeDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(atividadeDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(atividadeDTO);
    }

    @GetMapping
    public ResponseEntity<List<AtividadeDTO>> listarTodasAtividades() {
        return ResponseEntity.ok(atividadeService.listarAtividades());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeDTO> listarAtividadePorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(atividadeService.listarAtividadePorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAtividade(@PathVariable Long id) {
        atividadeService.deletarAtividade(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtividadeDTO> atualizarAtividade(@PathVariable Long id, @RequestBody AtividadeDTO atividadeDTO) {
        var atividadeAtualizada = atividadeService.atualizarAtividade(id, atividadeDTO);
        return ResponseEntity.ok().body(atividadeAtualizada);
    }
}
