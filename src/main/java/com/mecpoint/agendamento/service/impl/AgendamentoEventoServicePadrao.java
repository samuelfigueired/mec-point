package com.mecpoint.agendamento.service.impl;

import com.mecpoint.agendamento.dto.AgendamentoEventoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoEventoOutDTO;
import com.mecpoint.agendamento.entities.Agendamento;
import com.mecpoint.agendamento.entities.AgendamentoEvento;
import com.mecpoint.agendamento.mapper.AgendamentoEventoMapper;
import com.mecpoint.agendamento.repository.AgendamentoEventoRepository;
import com.mecpoint.agendamento.repository.AgendamentoRepository;
import com.mecpoint.agendamento.service.AgendamentoEventoService;
import com.mecpoint.core.exceptions.BusinessException;
import com.mecpoint.core.exceptions.ResourceNotFoundException;
import com.mecpoint.user.entities.User;
import com.mecpoint.user.entities.enums.UserRole;
import com.mecpoint.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AgendamentoEventoServicePadrao implements AgendamentoEventoService {

    private final AgendamentoEventoRepository repository;
    private final AgendamentoRepository agendamentoRepository;
    private final UserRepository userRepository;
    private final AgendamentoEventoMapper mapper;

    @Override
    public List<AgendamentoEventoOutDTO> listarPorAgendamento(Long agendamentoId) {
        buscarAgendamento(agendamentoId);

        return repository.findByAgendamentoIdOrderByDataEventoAsc(agendamentoId)
                .stream()
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public AgendamentoEventoOutDTO criar(Long agendamentoId, AgendamentoEventoInDTO dto) {
        Agendamento agendamento = buscarAgendamento(agendamentoId);
        User usuarioLogado = getUsuarioAutenticado();

        validarUsuarioPodeCriarEvento(usuarioLogado);

        AgendamentoEvento evento = mapper.toEntity(dto);
        evento.setAgendamento(agendamento);
        evento.setCriadoPor(usuarioLogado);

        if (evento.getDataEvento() == null) {
            evento.setDataEvento(LocalDateTime.now());
        }

        agendamento.setStatus(dto.getStatus());

        AgendamentoEvento eventoSalvo = repository.save(evento);

        return mapper.toOutDTO(eventoSalvo);
    }

    @Override
    public AgendamentoEventoOutDTO atualizar(Long eventoId, AgendamentoEventoInDTO dto) {
        AgendamentoEvento evento = buscarEvento(eventoId);
        User usuarioLogado = getUsuarioAutenticado();

        validarUsuarioPodeCriarEvento(usuarioLogado);

        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setStatus(dto.getStatus());

        if (dto.getDataEvento() != null) {
            evento.setDataEvento(dto.getDataEvento());
        }

        evento.setCriadoPor(usuarioLogado);

        evento.getAgendamento().setStatus(dto.getStatus());

        return mapper.toOutDTO(repository.save(evento));
    }

    @Override
    public void deletar(Long eventoId) {
        AgendamentoEvento evento = buscarEvento(eventoId);
        repository.delete(evento);
    }

    private Agendamento buscarAgendamento(Long agendamentoId) {
        return agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado: " + agendamentoId));
    }

    private AgendamentoEvento buscarEvento(Long eventoId) {
        return repository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento do agendamento não encontrado: " + eventoId));
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

    private void validarUsuarioPodeCriarEvento(User usuario) {
        if (UserRole.ADMIN.equals(usuario.getRole()) || UserRole.MECANICO.equals(usuario.getRole())) {
            return;
        }

        throw new BusinessException("O usuário autenticado não possui permissão para criar eventos.");
    }
}