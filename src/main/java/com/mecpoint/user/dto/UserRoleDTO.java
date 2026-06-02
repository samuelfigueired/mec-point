package com.mecpoint.user.dto;

import com.mecpoint.user.entities.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRoleDTO {

    @NotNull(message = "A permissão do usuário é obrigatória.")
    private UserRole role;
}