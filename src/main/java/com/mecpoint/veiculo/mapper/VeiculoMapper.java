package com.mecpoint.veiculo.mapper;

import com.mecpoint.core.mapper.BaseMapper;
import com.mecpoint.veiculo.dto.VeiculoInDTO;
import com.mecpoint.veiculo.dto.VeiculoOutDTO;
import com.mecpoint.veiculo.entities.Veiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VeiculoMapper extends BaseMapper<Veiculo, VeiculoInDTO, VeiculoOutDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Veiculo toEntity(VeiculoInDTO dto);

    @Override
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "usuarioNome", source = "usuario.nome")
    VeiculoOutDTO toOutDTO(Veiculo entity);
}