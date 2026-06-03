package com.mecpoint.servico.controller;

import com.mecpoint.servico.dto.ServicoInDTO;
import com.mecpoint.servico.dto.ServicoOutDTO;
import com.mecpoint.servico.service.ServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Endpoints de gerenciamento de serviços")
public class ServicoController {

    private final ServicoService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Lista todos os serviços")
    public ResponseEntity<List<ServicoOutDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/ativos")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Lista serviços ativos")
    public ResponseEntity<List<ServicoOutDTO>> listarAtivos() {
        return ResponseEntity.ok(service.listarAtivos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Busca um serviço por ID")
    public ResponseEntity<ServicoOutDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
    @Operation(summary = "Cria um novo serviço")
    public ResponseEntity<ServicoOutDTO> criar(@RequestBody @Valid ServicoInDTO dto) {
        ServicoOutDTO created = service.criar(dto);

        return ResponseEntity
                .created(URI.create("/servicos/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
    @Operation(summary = "Atualiza um serviço existente")
    public ResponseEntity<ServicoOutDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ServicoInDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove um serviço")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}