package com.mecpoint.agendamento.controller;

import com.mecpoint.agendamento.dto.AgendamentoEventoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoEventoOutDTO;
import com.mecpoint.agendamento.service.AgendamentoEventoService;
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
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Eventos do Agendamento", description = "Endpoints de histórico e timeline dos agendamentos")
@PreAuthorize("hasAnyRole('ADMIN', 'MECANICO')")
public class AgendamentoEventoController {

    private final AgendamentoEventoService service;

    @GetMapping("/{agendamentoId}/eventos")
    @Operation(summary = "Lista eventos de um agendamento")
    public ResponseEntity<List<AgendamentoEventoOutDTO>> listarPorAgendamento(@PathVariable Long agendamentoId) {
        return ResponseEntity.ok(service.listarPorAgendamento(agendamentoId));
    }

    @PostMapping("/{agendamentoId}/eventos")
    @Operation(summary = "Cria um evento para um agendamento")
    public ResponseEntity<AgendamentoEventoOutDTO> criar(
            @PathVariable Long agendamentoId,
            @RequestBody @Valid AgendamentoEventoInDTO dto
    ) {
        AgendamentoEventoOutDTO created = service.criar(agendamentoId, dto);

        return ResponseEntity
                .created(URI.create("/agendamentos/" + agendamentoId + "/eventos/" + created.getId()))
                .body(created);
    }

    @PutMapping("/eventos/{eventoId}")
    @Operation(summary = "Atualiza um evento do agendamento")
    public ResponseEntity<AgendamentoEventoOutDTO> atualizar(
            @PathVariable Long eventoId,
            @RequestBody @Valid AgendamentoEventoInDTO dto
    ) {
        return ResponseEntity.ok(service.atualizar(eventoId, dto));
    }

    @DeleteMapping("/eventos/{eventoId}")
    @Operation(summary = "Remove um evento do agendamento")
    public ResponseEntity<Void> deletar(@PathVariable Long eventoId) {
        service.deletar(eventoId);
        return ResponseEntity.noContent().build();
    }
}