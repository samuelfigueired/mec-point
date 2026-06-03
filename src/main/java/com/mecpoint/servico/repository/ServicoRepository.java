package com.mecpoint.servico.repository;

import com.mecpoint.servico.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByAtivoTrue();

    boolean existsByNomeIgnoreCase(String nome);
}