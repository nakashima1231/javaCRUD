package io.github.nakashima1231.lojaAPI.controller;

import io.github.nakashima1231.lojaAPI.dto.Venda.VendaRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.Venda.VendaResponseDTO;
import io.github.nakashima1231.lojaAPI.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@CrossOrigin
@RequiredArgsConstructor
public class VendaController {
    private final VendaService service;

    @GetMapping
    public List<VendaResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public VendaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<VendaResponseDTO> criar(@RequestBody @Valid VendaRequestDTO dados,
                                                  @AuthenticationPrincipal String email) {
        VendaResponseDTO venda = service.criarVenda(dados, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(venda);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.cancelar(id);
        return ResponseEntity.noContent().build(); // Retorna Status 204
    }

}
