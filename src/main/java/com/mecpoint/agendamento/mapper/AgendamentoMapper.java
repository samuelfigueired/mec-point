package com.mecpoint.agendamento.mapper;

import com.mecpoint.agendamento.dto.AgendamentoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoOutDTO;
import com.mecpoint.agendamento.entities.Agendamento;
import com.mecpoint.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgendamentoMapper extends BaseMapper<Agendamento, AgendamentoInDTO, AgendamentoOutDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroAgd", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "veiculo", ignore = true)
    @Mapping(target = "mecanico", ignore = true)
    @Mapping(target = "servicoRef", ignore = true)
    Agendamento toEntity(AgendamentoInDTO dto);

    @Override
    @Mapping(target = "veiculoId", source = "veiculo.id")
    @Mapping(target = "veiculoModelo", source = "veiculo.modelo")
    @Mapping(target = "veiculoMarca", source = "veiculo.marca")
    @Mapping(target = "veiculoPlaca", source = "veiculo.placa")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "usuarioNome", source = "usuario.nome")
    @Mapping(target = "mecanicoId", source = "mecanico.id")
    @Mapping(target = "mecanicoNome", source = "mecanico.nome")
    @Mapping(target = "servicoId", source = "servicoRef.id")
    @Mapping(target = "servicoNome", source = "servicoRef.nome")
    @Mapping(target = "servicoValor", source = "servicoRef.valor")
    AgendamentoOutDTO toOutDTO(Agendamento entity);
}