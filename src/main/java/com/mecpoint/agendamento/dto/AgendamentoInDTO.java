package com.mecpoint.agendamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoInDTO {

    @NotBlank(message = "O cliente é obrigatório.")
    private String cliente;

    @NotBlank(message = "O serviço é obrigatório.")
    private String servico;

    @NotNull(message = "A data e hora são obrigatórias.")
    private LocalDateTime dataHora;

    private String status;

    @NotNull(message = "O veículo é obrigatório.")
    private Long veiculoId;

    @NotNull(message = "O usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "O mecânico é obrigatório.")
    private Long mecanicoId;
}