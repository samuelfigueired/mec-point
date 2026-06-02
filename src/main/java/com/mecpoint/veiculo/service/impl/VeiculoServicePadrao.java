package com.mecpoint.veiculo.service.impl;

import com.mecpoint.core.exceptions.BusinessException;
import com.mecpoint.core.exceptions.ResourceNotFoundException;
import com.mecpoint.user.entities.User;
import com.mecpoint.user.repositories.UserRepository;
import com.mecpoint.veiculo.dto.VeiculoInDTO;
import com.mecpoint.veiculo.dto.VeiculoOutDTO;
import com.mecpoint.veiculo.entities.Veiculo;
import com.mecpoint.veiculo.mapper.VeiculoMapper;
import com.mecpoint.veiculo.repository.VeiculoRepository;
import com.mecpoint.veiculo.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VeiculoServicePadrao implements VeiculoService {

    private final VeiculoRepository repository;
    private final UserRepository userRepository;
    private final VeiculoMapper mapper;

    @Override
    public List<VeiculoOutDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public List<VeiculoOutDTO> listarPorUsuario(Long usuarioId) {
        buscarUsuario(usuarioId);

        return repository.findByUsuarioId(usuarioId)
                .stream()
                .map(mapper::toOutDTO)
                .toList();
    }

    @Override
    public VeiculoOutDTO buscarPorId(Long id) {
        return mapper.toOutDTO(buscarEntidadePorId(id));
    }

    @Override
    public Veiculo buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado: " + id));
    }

    @Override
    public VeiculoOutDTO criarVeiculo(VeiculoInDTO dto) {
        String placa = normalizarPlaca(dto.getPlaca());

        if (repository.existsByPlacaIgnoreCase(placa)) {
            throw new BusinessException("Já existe um veículo cadastrado com a placa: " + placa);
        }

        User usuario = buscarUsuario(dto.getUsuarioId());

        Veiculo entity = mapper.toEntity(dto);
        entity.setPlaca(placa);
        entity.setUsuario(usuario);

        return mapper.toOutDTO(repository.save(entity));
    }

    @Override
    public VeiculoOutDTO atualizarVeiculo(Long id, VeiculoInDTO dto) {
        Veiculo entity = buscarEntidadePorId(id);
        String placa = normalizarPlaca(dto.getPlaca());

        repository.findByPlacaIgnoreCase(placa)
                .filter(veiculo -> !veiculo.getId().equals(id))
                .ifPresent(veiculo -> {
                    throw new BusinessException("Já existe um veículo cadastrado com a placa: " + placa);
                });

        User usuario = buscarUsuario(dto.getUsuarioId());

        entity.setModelo(dto.getModelo());
        entity.setAno(dto.getAno());
        entity.setMarca(dto.getMarca());
        entity.setCambio(dto.getCambio());
        entity.setPlaca(placa);
        entity.setUsuario(usuario);

        return mapper.toOutDTO(repository.save(entity));
    }

    @Override
    public void deletarVeiculo(Long id) {
        Veiculo veiculo = buscarEntidadePorId(id);
        repository.delete(veiculo);
    }

    private User buscarUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new ResourceNotFoundException("Usuário do veículo não informado.");
        }

        return userRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + usuarioId));
    }

    private String normalizarPlaca(String placa) {
        return placa == null ? null : placa.replace("-", "").trim().toUpperCase();
    }
}