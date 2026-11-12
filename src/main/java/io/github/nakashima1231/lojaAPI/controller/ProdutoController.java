package io.github.nakashima1231.lojaAPI.controller;

import io.github.nakashima1231.lojaAPI.dto.ClienteDTO.ClienteRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.ClienteDTO.ClienteResponseDTO;
import io.github.nakashima1231.lojaAPI.dto.ProdutoDTO.ProdutoRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.ProdutoDTO.ProdutoResponseDTO;
import io.github.nakashima1231.lojaAPI.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService service;

    @GetMapping
    public List<ProdutoResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping()
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody @Valid ProdutoRequestDTO dados) {
        ProdutoResponseDTO produto = service.criarProduto(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO produto) {
        return service.atualizar(produto, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna Status 204
    }
}
