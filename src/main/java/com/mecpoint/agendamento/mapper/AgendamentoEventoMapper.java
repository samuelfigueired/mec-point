package com.mecpoint.agendamento.mapper;

import com.mecpoint.agendamento.dto.AgendamentoEventoInDTO;
import com.mecpoint.agendamento.dto.AgendamentoEventoOutDTO;
import com.mecpoint.agendamento.entities.AgendamentoEvento;
import com.mecpoint.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgendamentoEventoMapper extends BaseMapper<AgendamentoEvento, AgendamentoEventoInDTO, AgendamentoEventoOutDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    AgendamentoEvento toEntity(AgendamentoEventoInDTO dto);

    @Override
    @Mapping(target = "agendamentoId", source = "agendamento.id")
    @Mapping(target = "numeroAgendamento", source = "agendamento.numeroAgd")
    @Mapping(target = "criadoPorId", source = "criadoPor.id")
    @Mapping(target = "criadoPorNome", source = "criadoPor.nome")
    AgendamentoEventoOutDTO toOutDTO(AgendamentoEvento entity);
}