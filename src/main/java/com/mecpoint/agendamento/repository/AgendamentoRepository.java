package com.mecpoint.agendamento.repository;

import com.mecpoint.agendamento.entities.Agendamento;
import com.mecpoint.agendamento.entities.enums.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByUsuarioId(Long usuarioId);

    List<Agendamento> findByVeiculoId(Long veiculoId);

    List<Agendamento> findByMecanicoId(Long mecanicoId);

    List<Agendamento> findByStatus(StatusAgendamento status);

    List<Agendamento> findByUsuarioIdAndStatus(Long usuarioId, StatusAgendamento status);

    List<Agendamento> findByMecanicoIdAndStatus(Long mecanicoId, StatusAgendamento status);

    long countByMecanicoId(Long mecanicoId);

    long countByMecanicoIdAndStatus(Long mecanicoId, StatusAgendamento status);
}