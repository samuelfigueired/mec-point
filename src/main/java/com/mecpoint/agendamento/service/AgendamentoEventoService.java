package com.mecpoint.agendamento.service;

import com.mecpoint.agendamento.dto.AgendamentoEventoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoEventoOutDTO;

import java.util.List;

public interface AgendamentoEventoService {

    List<AgendamentoEventoOutDTO> listarPorAgendamento(Long agendamentoId);

    AgendamentoEventoOutDTO criar(Long agendamentoId, AgendamentoEventoInDTO dto);

    AgendamentoEventoOutDTO atualizar(Long eventoId, AgendamentoEventoInDTO dto);

    void deletar(Long eventoId);
}