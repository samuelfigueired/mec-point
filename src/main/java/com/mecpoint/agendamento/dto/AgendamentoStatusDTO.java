package com.mecpoint.agendamento.dto;

import com.mecpoint.agendamento.entities.enums.StatusAgendamento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgendamentoStatusDTO {

    @NotNull(message = "O status é obrigatório.")
    private StatusAgendamento status;
}