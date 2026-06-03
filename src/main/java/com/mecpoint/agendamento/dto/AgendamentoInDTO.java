package com.mecpoint.agendamento.dto;

import com.mecpoint.agendamento.entities.enums.StatusAgendamento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoInDTO {

    private String cliente;

    private String servico;

    @NotNull(message = "A data e hora são obrigatórias.")
    private LocalDateTime dataHora;

    private StatusAgendamento status;

    @NotNull(message = "O veículo é obrigatório.")
    private Long veiculoId;

    @NotNull(message = "O usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "O mecânico é obrigatório.")
    private Long mecanicoId;

    private Long servicoId;
}