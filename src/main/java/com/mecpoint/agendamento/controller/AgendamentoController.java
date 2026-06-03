package com.mecpoint.agendamento.controller;

import com.mecpoint.agendamento.dto.AgendamentoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoOutDTO;
import com.mecpoint.agendamento.entities.enums.StatusAgendamento;
import com.mecpoint.agendamento.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints de gerenciamento de agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
    @Operation(summary = "Lista todos os agendamentos")
    public ResponseEntity<List<AgendamentoOutDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Lista agendamentos de um usuário")
    public ResponseEntity<List<AgendamentoOutDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/veiculo/{veiculoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Lista agendamentos de um veículo")
    public ResponseEntity<List<AgendamentoOutDTO>> listarPorVeiculo(@PathVariable Long veiculoId) {
        return ResponseEntity.ok(service.listarPorVeiculo(veiculoId));
    }

    @GetMapping("/mecanico/{mecanicoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
    @Operation(summary = "Lista agendamentos de um mecânico")
    public ResponseEntity<List<AgendamentoOutDTO>> listarPorMecanico(@PathVariable Long mecanicoId) {
        return ResponseEntity.ok(service.listarPorMecanico(mecanicoId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Busca um agendamento por ID")
    public ResponseEntity<AgendamentoOutDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Cria um novo agendamento")
    public ResponseEntity<AgendamentoOutDTO> criar(@RequestBody @Valid AgendamentoInDTO dto) {
        return ResponseEntity.ok(service.criarAgendamento(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
    @Operation(summary = "Atualiza um agendamento existente")
    public ResponseEntity<AgendamentoOutDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AgendamentoInDTO dto
    ) {
        return ResponseEntity.ok(service.atualizarAgendamento(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove um agendamento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
    @Operation(summary = "Lista agendamentos por status")
    public ResponseEntity<List<AgendamentoOutDTO>> listarPorStatus(@PathVariable StatusAgendamento status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }

    @GetMapping("/usuario/{usuarioId}/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO', 'USER')")
    @Operation(summary = "Lista agendamentos de um usuário por status")
    public ResponseEntity<List<AgendamentoOutDTO>> listarPorUsuarioEStatus(
            @PathVariable Long usuarioId,
            @PathVariable StatusAgendamento status
    ) {
        return ResponseEntity.ok(service.listarPorUsuarioEStatus(usuarioId, status));
    }

    @GetMapping("/mecanico/{mecanicoId}/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
    @Operation(summary = "Lista agendamentos de um mecânico por status")
    public ResponseEntity<List<AgendamentoOutDTO>> listarPorMecanicoEStatus(
            @PathVariable Long mecanicoId,
            @PathVariable StatusAgendamento status
    ) {
        return ResponseEntity.ok(service.listarPorMecanicoEStatus(mecanicoId, status));
    }
}