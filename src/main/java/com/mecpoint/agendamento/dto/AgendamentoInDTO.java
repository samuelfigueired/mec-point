package com.mecpoint.agendamento.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoInDTO {

    @NotBlank(message = "O nome do cliente é obrigatório.")
    private String cliente;

    @NotBlank(message = "O serviço é obrigatório.")
    private String servico;

    @NotNull(message = "A data e hora do agendamento são obrigatórias.")
    @Future(message = "A data e hora devem ser futuras.")
    private LocalDateTime dataHora;

    private String status;

    private Long veiculoId;

    @NotNull(message = "O usuário do agendamento é obrigatório.")
    private Long usuarioId;
}