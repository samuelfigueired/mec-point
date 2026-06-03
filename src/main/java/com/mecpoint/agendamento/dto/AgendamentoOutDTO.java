package com.mecpoint.agendamento.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoOutDTO {

    private Long id;
    private String numeroAgd;
    private String cliente;
    private String servico;
    private LocalDateTime dataHora;
    private String status;

    private Long veiculoId;
    private String veiculoModelo;
    private String veiculoMarca;
    private String veiculoPlaca;

    private Long usuarioId;
    private String usuarioNome;

    private Long mecanicoId;
    private String mecanicoNome;
}