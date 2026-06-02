package com.mecpoint.agendamento.service.impl;

import com.mecpoint.agendamento.dto.AgendamentoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoOutDTO;
import com.mecpoint.agendamento.entities.Agendamento;
import com.mecpoint.agendamento.mapper.AgendamentoMapper;
import com.mecpoint.agendamento.repository.AgendamentoRepository;
import com.mecpoint.agendamento.service.AgendamentoService;
import com.mecpoint.user.entities.User;
import com.mecpoint.user.repositories.UserRepository;
import com.mecpoint.veiculo.entities.Veiculo;
import com.mecpoint.veiculo.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AgendamentoServicePadrao implements AgendamentoService {

    private final AgendamentoRepository repository;
    private final VeiculoRepository veiculoRepository;
    private final UserRepository userRepository;
    private final AgendamentoMapper mapper;

    @Override
    public List<AgendamentoOutDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public List<AgendamentoOutDTO> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public AgendamentoOutDTO criarAgendamento(AgendamentoInDTO dto) {
        Agendamento entity = mapper.toEntity(dto);

        entity.setVeiculo(buscarVeiculo(dto.getVeiculoId()));
        entity.setUsuario(buscarUsuario(dto.getUsuarioId()));

        String ano = String.valueOf(Year.now().getValue());
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        entity.setNumeroAgd(String.format("AGD-%s-%s", ano, random));

        if (entity.getStatus() == null || entity.getStatus().isBlank()) {
            entity.setStatus("PENDENTE");
        }

        return mapper.toOutDTO(repository.save(entity));
    }

    @Override
    public AgendamentoOutDTO atualizarAgendamento(Long id, AgendamentoInDTO dto) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado: " + id));

        agendamento.setCliente(dto.getCliente());
        agendamento.setServico(dto.getServico());
        agendamento.setDataHora(dto.getDataHora());

        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            agendamento.setStatus("PENDENTE");
        } else {
            agendamento.setStatus(dto.getStatus());
        }

        agendamento.setVeiculo(buscarVeiculo(dto.getVeiculoId()));
        agendamento.setUsuario(buscarUsuario(dto.getUsuarioId()));

        return mapper.toOutDTO(repository.save(agendamento));
    }

    @Override
    public void deletarAgendamento(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado: " + id);
        }

        repository.deleteById(id);
    }

    private Veiculo buscarVeiculo(Long veiculoId) {
        if (veiculoId == null) {
            return null;
        }

        return veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + veiculoId));
    }

    private User buscarUsuario(Long usuarioId) {
        return userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usuarioId));
    }
    @Override
    public List<AgendamentoOutDTO> listarPorVeiculo(Long veiculoId) {
        return repository.findByVeiculoId(veiculoId)
                .stream()
                .map(mapper::toOutDTO)
                .toList();
    }
}