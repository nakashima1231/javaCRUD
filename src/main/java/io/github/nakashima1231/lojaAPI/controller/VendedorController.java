package io.github.nakashima1231.lojaAPI.controller;

import io.github.nakashima1231.lojaAPI.dto.Vendedor.VendedorRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.Vendedor.VendedorResponseDTO;
import io.github.nakashima1231.lojaAPI.service.VendedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendedores")
@CrossOrigin
@RequiredArgsConstructor
public class VendedorController {
    private final VendedorService service;

    @GetMapping
    public List<VendedorResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public VendedorResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<VendedorResponseDTO> criar(@RequestBody @Valid VendedorRequestDTO dados) {
        VendedorResponseDTO vendedor = service.criarVendedor(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(vendedor);
    }

    @PutMapping("/{id}")
    public VendedorResponseDTO atualizar(@RequestBody @Valid VendedorRequestDTO dados, @PathVariable Long id) {
        return service.atualizar(dados, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna Status 204
    }



}
