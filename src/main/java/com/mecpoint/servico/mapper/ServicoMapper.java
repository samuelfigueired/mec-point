package com.mecpoint.servico.mapper;

import com.mecpoint.core.mapper.BaseMapper;
import com.mecpoint.servico.dto.ServicoInDTO;
import com.mecpoint.servico.dto.ServicoOutDTO;
import com.mecpoint.servico.entities.Servico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServicoMapper extends BaseMapper<Servico, ServicoInDTO, ServicoOutDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    Servico toEntity(ServicoInDTO dto);
}