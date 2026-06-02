package com.mecpoint.user.service.impl;

import com.mecpoint.core.exceptions.BusinessException;
import com.mecpoint.core.exceptions.ResourceNotFoundException;
import com.mecpoint.user.dto.UserInDTO;
import com.mecpoint.user.dto.UserOutDTO;
import com.mecpoint.user.entities.User;
import com.mecpoint.user.entities.enums.UserRole;
import com.mecpoint.user.mapper.UserMapper;
import com.mecpoint.user.repositories.UserRepository;
import com.mecpoint.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServicePadrao implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public UserOutDTO criar(UserInDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("E-mail já cadastrado.");
        }

        User user = mapper.toEntity(dto);

        user.setSenha(encoder.encode(dto.getSenha()));
        user.setRole(UserRole.USER);
        user.setAtivo(true);

        return mapper.toOutDTO(repository.save(user));
    }

    @Override
    public List<UserOutDTO> listar() {
        return mapper.toOutDTOList(repository.findAll());
    }

    @Override
    public UserOutDTO buscarPorId(Long id) {
        User user = buscarEntidadePorId(id);
        return mapper.toOutDTO(user);
    }

    @Override
    public UserOutDTO atualizar(Long id, UserInDTO dto) {
        User user = buscarEntidadePorId(id);

        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            user.setNome(dto.getNome());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            validarEmailDisponivelParaAtualizacao(id, dto.getEmail());
            user.setEmail(dto.getEmail());
        }

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            user.setSenha(encoder.encode(dto.getSenha()));
        }

        return mapper.toOutDTO(repository.save(user));
    }

    @Override
    public void deletar(Long id) {
        User user = buscarEntidadePorId(id);
        repository.delete(user);
    }

    @Override
    public UserOutDTO atualizarRole(Long id, UserRole role) {
        User user = buscarEntidadePorId(id);
        user.setRole(role);

        return mapper.toOutDTO(repository.save(user));
    }

    private User buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
    }

    private void validarEmailDisponivelParaAtualizacao(Long id, String email) {
        repository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new BusinessException("E-mail já cadastrado.");
                });
    }
}