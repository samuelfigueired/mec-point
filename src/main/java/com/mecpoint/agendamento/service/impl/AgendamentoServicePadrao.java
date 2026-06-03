package com.mecpoint.agendamento.service.impl;

import com.mecpoint.agendamento.dto.AgendamentoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoOutDTO;
import com.mecpoint.agendamento.entities.Agendamento;
import com.mecpoint.agendamento.entities.enums.StatusAgendamento;
import com.mecpoint.agendamento.mapper.AgendamentoMapper;
import com.mecpoint.agendamento.repository.AgendamentoRepository;
import com.mecpoint.agendamento.service.AgendamentoService;
import com.mecpoint.core.exceptions.BusinessException;
import com.mecpoint.core.exceptions.ResourceNotFoundException;
import com.mecpoint.servico.entities.Servico;
import com.mecpoint.servico.repository.ServicoRepository;
import com.mecpoint.user.entities.User;
import com.mecpoint.user.entities.enums.UserRole;
import com.mecpoint.user.repositories.UserRepository;
import com.mecpoint.veiculo.entities.Veiculo;
import com.mecpoint.veiculo.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ServicoRepository servicoRepository;
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
        validarPermissaoListagemPorUsuario(usuarioId);

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
                .filter(this::possuiPermissaoVisualizacao)
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public List<AgendamentoOutDTO> listarPorMecanico(Long mecanicoId) {
        buscarMecanico(mecanicoId);

        return repository.findByMecanicoId(mecanicoId)
                .stream()
                .filter(this::possuiPermissaoVisualizacao)
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public AgendamentoOutDTO buscarPorId(Long id) {
        Agendamento agendamento = buscarEntidadePorId(id);

        validarPermissaoVisualizacao(agendamento);

        return mapper.toOutDTO(agendamento);
    }

    @Override
    public AgendamentoOutDTO criarAgendamento(AgendamentoInDTO dto) {
        Agendamento entity = mapper.toEntity(dto);

        entity.setCliente(dto.getCliente());
        entity.setDescricao(dto.getDescricao());
        entity.setDataHora(dto.getDataHora());

        entity.setVeiculo(buscarVeiculo(dto.getVeiculoId()));
        entity.setUsuario(buscarUsuario(dto.getUsuarioId()));
        entity.setMecanico(buscarMecanico(dto.getMecanicoId()));

        aplicarServico(entity, dto);

        entity.setNumeroAgd(gerarNumeroAgendamento());

        if (dto.getStatus() == null) {
            entity.setStatus(StatusAgendamento.PENDENTE);
        } else {
            entity.setStatus(dto.getStatus());
        }

        return mapper.toOutDTO(repository.save(entity));
    }

    @Override
    public AgendamentoOutDTO atualizarAgendamento(Long id, AgendamentoInDTO dto) {
        Agendamento agendamento = buscarEntidadePorId(id);

        validarPermissaoAlteracao(agendamento);

        agendamento.setCliente(dto.getCliente());
        agendamento.setDescricao(dto.getDescricao());
        agendamento.setDataHora(dto.getDataHora());

        if (dto.getStatus() == null) {
            agendamento.setStatus(StatusAgendamento.PENDENTE);
        } else {
            agendamento.setStatus(dto.getStatus());
        }

        agendamento.setVeiculo(buscarVeiculo(dto.getVeiculoId()));
        agendamento.setUsuario(buscarUsuario(dto.getUsuarioId()));
        agendamento.setMecanico(buscarMecanico(dto.getMecanicoId()));

        aplicarServico(agendamento, dto);

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
            throw new ResourceNotFoundException("Veículo do agendamento não informado.");
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

    private Servico buscarServicoOpcional(Long servicoId) {
        if (servicoId == null) {
            return null;
        }

        return servicoRepository.findById(servicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado: " + servicoId));
    }

    private void aplicarServico(Agendamento agendamento, AgendamentoInDTO dto) {
        Servico servico = buscarServicoOpcional(dto.getServicoId());

        if (servico != null) {
            agendamento.setServicoRef(servico);
            agendamento.setServico(servico.getNome());
            return;
        }

        agendamento.setServicoRef(null);

        if (dto.getServico() == null || dto.getServico().isBlank()) {
            agendamento.setServico("Avaliação inicial");
        } else {
            agendamento.setServico(dto.getServico());
        }
    }

    private User getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new BusinessException("Usuário não autenticado.");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autenticado não encontrado."));
    }

    private void validarPermissaoVisualizacao(Agendamento agendamento) {
        User usuarioLogado = getUsuarioAutenticado();

        if (UserRole.ADMIN.equals(usuarioLogado.getRole())) {
            return;
        }

        if (UserRole.MECANICO.equals(usuarioLogado.getRole())) {
            if (agendamento.getMecanico() != null &&
                    agendamento.getMecanico().getId().equals(usuarioLogado.getId())) {
                return;
            }

            throw new BusinessException("Você não possui permissão para visualizar este agendamento.");
        }

        if (UserRole.USER.equals(usuarioLogado.getRole())) {
            if (agendamento.getUsuario() != null &&
                    agendamento.getUsuario().getId().equals(usuarioLogado.getId())) {
                return;
            }

            throw new BusinessException("Você não possui permissão para visualizar este agendamento.");
        }

        throw new BusinessException("Perfil de usuário sem permissão para visualizar agendamentos.");
    }

    private boolean possuiPermissaoVisualizacao(Agendamento agendamento) {
        try {
            validarPermissaoVisualizacao(agendamento);
            return true;
        } catch (BusinessException e) {
            return false;
        }
    }

    private void validarPermissaoListagemPorUsuario(Long usuarioId) {
        User usuarioLogado = getUsuarioAutenticado();

        if (UserRole.ADMIN.equals(usuarioLogado.getRole()) || UserRole.MECANICO.equals(usuarioLogado.getRole())) {
            return;
        }

        if (UserRole.USER.equals(usuarioLogado.getRole()) && usuarioLogado.getId().equals(usuarioId)) {
            return;
        }

        throw new BusinessException("Você não possui permissão para listar agendamentos de outro usuário.");
    }

    private void validarPermissaoAlteracao(Agendamento agendamento) {
        User usuarioLogado = getUsuarioAutenticado();

        if (UserRole.ADMIN.equals(usuarioLogado.getRole())) {
            return;
        }

        if (UserRole.MECANICO.equals(usuarioLogado.getRole())) {
            if (agendamento.getMecanico() != null &&
                    agendamento.getMecanico().getId().equals(usuarioLogado.getId())) {
                return;
            }

            throw new BusinessException("Você não possui permissão para alterar este agendamento.");
        }

        throw new BusinessException("Você não possui permissão para alterar agendamentos.");
    }

    private String gerarNumeroAgendamento() {
        String ano = String.valueOf(Year.now().getValue());
        String random = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        return String.format("AGD-%s-%s", ano, random);
    }
}