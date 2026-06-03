package com.mecpoint.servico.service.impl;

import com.mecpoint.core.exceptions.BusinessException;
import com.mecpoint.core.exceptions.ResourceNotFoundException;
import com.mecpoint.servico.dto.ServicoInDTO;
import com.mecpoint.servico.dto.ServicoOutDTO;
import com.mecpoint.servico.entities.Servico;
import com.mecpoint.servico.mapper.ServicoMapper;
import com.mecpoint.servico.repository.ServicoRepository;
import com.mecpoint.servico.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServicoServicePadrao implements ServicoService {

    private final ServicoRepository repository;
    private final ServicoMapper mapper;

    @Override
    public List<ServicoOutDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public List<ServicoOutDTO> listarAtivos() {
        return repository.findByAtivoTrue()
                .stream()
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public ServicoOutDTO buscarPorId(Long id) {
        return mapper.toOutDTO(buscarEntidadePorId(id));
    }

    @Override
    public ServicoOutDTO criar(ServicoInDTO dto) {
        if (repository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new BusinessException("Já existe um serviço cadastrado com este nome.");
        }

        Servico entity = mapper.toEntity(dto);

        if (entity.getAtivo() == null) {
            entity.setAtivo(true);
        }

        return mapper.toOutDTO(repository.save(entity));
    }

    @Override
    public ServicoOutDTO atualizar(Long id, ServicoInDTO dto) {
        Servico entity = buscarEntidadePorId(id);

        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setValor(dto.getValor());
        entity.setCategoria(dto.getCategoria());

        if (dto.getAtivo() != null) {
            entity.setAtivo(dto.getAtivo());
        }

        return mapper.toOutDTO(repository.save(entity));
    }

    @Override
    public void deletar(Long id) {
        Servico entity = buscarEntidadePorId(id);
        repository.delete(entity);
    }

    private Servico buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado: " + id));
    }
}