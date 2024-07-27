package com.github.arturtcs.gerenciamentodeprojetos.resources;

import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.dto.ClienteDTO;
import com.github.arturtcs.gerenciamentodeprojetos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        clienteDTO = clienteService.cadastrarCliente(clienteDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteDTO.id())
                .toUri();
        return ResponseEntity.created(uri).body(clienteDTO);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodosOsClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> listarClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(clienteService.listarClientePorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDadosAtualizados) {
        var clienteAtualizado = clienteService.atualizarCliente(id, clienteDadosAtualizados);
        return ResponseEntity.ok().body(clienteAtualizado);
    }
}
