package com.mecpoint.agendamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDashboardResumoDTO {

    private Long total;
    private Long pendentes;
    private Long agendados;
    private Long confirmados;
    private Long emAndamento;
    private Long quaseFinalizados;
    private Long finalizados;
    private Long cancelados;
}