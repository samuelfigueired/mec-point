package com.mecpoint.agendamento.service;

import com.mecpoint.agendamento.dto.AgendamentoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoOutDTO;

import java.util.List;

public interface AgendamentoService {

    List<AgendamentoOutDTO> listarTodos();

    List<AgendamentoOutDTO> listarPorUsuario(Long usuarioId);

    List<AgendamentoOutDTO> listarPorVeiculo(Long veiculoId);

    List<AgendamentoOutDTO> listarPorMecanico(Long mecanicoId);

    AgendamentoOutDTO buscarPorId(Long id);

    AgendamentoOutDTO criarAgendamento(AgendamentoInDTO dto);

    AgendamentoOutDTO atualizarAgendamento(Long id, AgendamentoInDTO dto);

    void deletarAgendamento(Long id);
}