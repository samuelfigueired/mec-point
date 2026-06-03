package com.mecpoint.agendamento.service;

import com.mecpoint.agendamento.dto.AgendamentoDashboardResumoDTO;
import com.mecpoint.agendamento.dto.AgendamentoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoOutDTO;
import com.mecpoint.agendamento.entities.enums.StatusAgendamento;

import java.util.List;

public interface AgendamentoService {

    List<AgendamentoOutDTO> listarTodos();

    List<AgendamentoOutDTO> listarMeusAgendamentos();

    List<AgendamentoOutDTO> listarPorUsuario(Long usuarioId);

    List<AgendamentoOutDTO> listarPorVeiculo(Long veiculoId);

    List<AgendamentoOutDTO> listarPorMecanico(Long mecanicoId);

    List<AgendamentoOutDTO> listarPorStatus(StatusAgendamento status);

    List<AgendamentoOutDTO> listarPorUsuarioEStatus(Long usuarioId, StatusAgendamento status);

    List<AgendamentoOutDTO> listarPorMecanicoEStatus(Long mecanicoId, StatusAgendamento status);

    AgendamentoOutDTO buscarPorId(Long id);

    AgendamentoDashboardResumoDTO buscarResumoDashboardMecanicoLogado();

    AgendamentoDashboardResumoDTO buscarResumoDashboardPorMecanico(Long mecanicoId);

    AgendamentoOutDTO criarAgendamento(AgendamentoInDTO dto);

    AgendamentoOutDTO atualizarAgendamento(Long id, AgendamentoInDTO dto);

    void deletarAgendamento(Long id);
}