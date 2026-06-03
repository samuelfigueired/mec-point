package com.mecpoint.user.service;

import com.mecpoint.user.dto.UserInDTO;
import com.mecpoint.user.dto.UserOutDTO;
import com.mecpoint.user.entities.enums.UserRole;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface UserService {

    UserOutDTO criar(UserInDTO dto);

    List<UserOutDTO> listar();

    UserOutDTO buscarPorId(Long id);

    UserOutDTO atualizar(Long id, UserInDTO dto);

    void deletar(Long id);

    UserOutDTO atualizarRole(Long id, UserRole role);

    List<UserOutDTO> listarMecanicos();
}
