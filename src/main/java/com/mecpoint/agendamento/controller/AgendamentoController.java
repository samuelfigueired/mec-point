package com.mecpoint.agendamento.controller;

import com.mecpoint.agendamento.dto.AgendamentoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoOutDTO;
import com.mecpoint.agendamento.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@Tag(name = "Agendamentos", description = "Endpoints de gerenciamento de agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;

    @GetMapping
    @Operation(summary = "Lista todos os agendamentos")
    public ResponseEntity<List<AgendamentoOutDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Lista agendamentos de um usuário")
    public ResponseEntity<List<AgendamentoOutDTO>> listarPorUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorUsuario(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo agendamento")
    public ResponseEntity<AgendamentoOutDTO> criar(@RequestBody AgendamentoInDTO dto) {
        return ResponseEntity.ok(service.criarAgendamento(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um agendamento existente")
    public ResponseEntity<AgendamentoOutDTO> atualizar(@PathVariable Long id, @RequestBody AgendamentoInDTO dto) {
        return ResponseEntity.ok(service.atualizarAgendamento(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um agendamento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/veiculo/{veiculoId}")
    @Operation(summary = "Lista agendamentos de um veículo")
    public ResponseEntity<List<AgendamentoOutDTO>> listarPorVeiculo(@PathVariable Long veiculoId) {
        return ResponseEntity.ok(service.listarPorVeiculo(veiculoId));
    }
}