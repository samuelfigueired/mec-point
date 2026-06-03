package com.mecpoint.agendamento.dto;

import com.mecpoint.agendamento.entities.enums.StatusAgendamento;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoEventoOutDTO {

    private Long id;

    private Long agendamentoId;
    private String numeroAgendamento;

    private String titulo;
    private String descricao;
    private StatusAgendamento status;
    private LocalDateTime dataEvento;

    private Long criadoPorId;
    private String criadoPorNome;
}