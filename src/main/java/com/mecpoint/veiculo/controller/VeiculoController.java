package com.mecpoint.veiculo.controller;

import com.mecpoint.veiculo.dto.VeiculoInDTO;
import com.mecpoint.veiculo.dto.VeiculoOutDTO;
import com.mecpoint.veiculo.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
@Tag(name = "Veículos", description = "Endpoints de gerenciamento de veículos")
public class VeiculoController {

    private final VeiculoService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
    @Operation(summary = "Lista todos os veículos")
    public ResponseEntity<List<VeiculoOutDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/meus")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Lista veículos do usuário autenticado")
    public ResponseEntity<List<VeiculoOutDTO>> listarMeusVeiculos() {
        return ResponseEntity.ok(service.listarMeusVeiculos());
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Lista veículos de um usuário")
    public ResponseEntity<List<VeiculoOutDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Busca um veículo por ID")
    public ResponseEntity<VeiculoOutDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Cria um novo veículo")
    public ResponseEntity<VeiculoOutDTO> criar(@RequestBody @Valid VeiculoInDTO dto) {
        return ResponseEntity.ok(service.criarVeiculo(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Atualiza um veículo existente")
    public ResponseEntity<VeiculoOutDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid VeiculoInDTO dto
    ) {
        return ResponseEntity.ok(service.atualizarVeiculo(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Remove um veículo")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarVeiculo(id);
        return ResponseEntity.noContent().build();
    }
}