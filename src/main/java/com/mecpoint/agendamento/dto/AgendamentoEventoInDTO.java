package com.mecpoint.agendamento.dto;

import com.mecpoint.agendamento.entities.enums.StatusAgendamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoEventoInDTO {

    @NotBlank(message = "O título do evento é obrigatório.")
    private String titulo;

    private String descricao;

    @NotNull(message = "O status do evento é obrigatório.")
    private StatusAgendamento status;

    private LocalDateTime dataEvento;

    private Long criadoPorId;
}