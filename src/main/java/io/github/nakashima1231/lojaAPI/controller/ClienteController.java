package io.github.nakashima1231.lojaAPI.controller;

import io.github.nakashima1231.lojaAPI.dto.ClienteDTO.ClienteRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.ClienteDTO.ClienteResponseDTO;
import io.github.nakashima1231.lojaAPI.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;

    @GetMapping
    public List<ClienteResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ClienteResponseDTO criar(@RequestBody @Valid ClienteRequestDTO dados) {
        return service.criar(dados);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO atualizar(@RequestBody @Valid ClienteRequestDTO dados, @PathVariable Long id) {
        return service.atualizar(dados, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna Status 204
    }

}
