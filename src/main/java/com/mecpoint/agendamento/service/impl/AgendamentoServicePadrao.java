package com.mecpoint.agendamento.service.impl;

import com.mecpoint.agendamento.dto.AgendamentoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoOutDTO;
import com.mecpoint.agendamento.entities.Agendamento;
import com.mecpoint.agendamento.mapper.AgendamentoMapper;
import com.mecpoint.agendamento.repository.AgendamentoRepository;
import com.mecpoint.agendamento.service.AgendamentoService;
import com.mecpoint.core.exceptions.ResourceNotFoundException;
import com.mecpoint.user.entities.User;
import com.mecpoint.user.repositories.UserRepository;
import com.mecpoint.veiculo.entities.Veiculo;
import com.mecpoint.veiculo.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mecpoint.core.exceptions.BusinessException;
import com.mecpoint.user.entities.enums.UserRole;

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
        buscarUsuario(usuarioId);

        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public List<AgendamentoOutDTO> listarPorVeiculo(Long veiculoId) {
        buscarVeiculo(veiculoId);

        return repository.findByVeiculoId(veiculoId)
                .stream()
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public AgendamentoOutDTO criarAgendamento(AgendamentoInDTO dto) {
        Agendamento entity = mapper.toEntity(dto);

        entity.setVeiculo(buscarVeiculo(dto.getVeiculoId()));
        entity.setUsuario(buscarUsuario(dto.getUsuarioId()));
        entity.setMecanico(buscarMecanico(dto.getMecanicoId()));

        entity.setNumeroAgd(gerarNumeroAgendamento());

        if (entity.getStatus() == null || entity.getStatus().isBlank()) {
            entity.setStatus("PENDENTE");
        }

        return mapper.toOutDTO(repository.save(entity));
    }
    @Override
    public AgendamentoOutDTO atualizarAgendamento(Long id, AgendamentoInDTO dto) {
        Agendamento agendamento = buscarEntidadePorId(id);

        agendamento.setCliente(dto.getCliente());
        agendamento.setServico(dto.getServico());
        agendamento.setDataHora(dto.getDataHora());

        agendamento.setMecanico(buscarMecanico(dto.getMecanicoId()));

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
        Agendamento agendamento = buscarEntidadePorId(id);
        repository.delete(agendamento);
    }

    private Agendamento buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado: " + id));
    }

    private Veiculo buscarVeiculo(Long veiculoId) {
        if (veiculoId == null) {
            return null;
        }

        return veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado: " + veiculoId));
    }

    private User buscarUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new ResourceNotFoundException("Usuário do agendamento não informado.");
        }

        return userRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + usuarioId));
    }

    private User buscarMecanico(Long mecanicoId) {
        if (mecanicoId == null) {
            throw new ResourceNotFoundException("Mecânico do agendamento não informado.");
        }

        User mecanico = userRepository.findById(mecanicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Mecânico não encontrado: " + mecanicoId));

        if (!UserRole.MECANICO.equals(mecanico.getRole())) {
            throw new BusinessException("O usuário informado não possui perfil de mecânico.");
        }

        return mecanico;
    }
    private String gerarNumeroAgendamento() {
        String ano = String.valueOf(Year.now().getValue());
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        return String.format("AGD-%s-%s", ano, random);
    }
}