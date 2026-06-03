package com.mecpoint.agendamento.repository;

import com.mecpoint.agendamento.entities.AgendamentoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoEventoRepository extends JpaRepository<AgendamentoEvento, Long> {

    List<AgendamentoEvento> findByAgendamentoIdOrderByDataEventoAsc(Long agendamentoId);
}