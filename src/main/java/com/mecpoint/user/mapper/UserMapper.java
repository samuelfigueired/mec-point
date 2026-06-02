package com.mecpoint.user.mapper;

import com.mecpoint.core.mapper.BaseMapper;
import com.mecpoint.user.dto.UserInDTO;
import com.mecpoint.user.dto.UserOutDTO;
import com.mecpoint.user.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserInDTO, UserOutDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    User toEntity(UserInDTO dto);
}