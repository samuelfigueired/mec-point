package com.mecpoint.servico.service;

import com.mecpoint.servico.dto.ServicoInDTO;
import com.mecpoint.servico.dto.ServicoOutDTO;

import java.util.List;

public interface ServicoService {

    List<ServicoOutDTO> listarTodos();

    List<ServicoOutDTO> listarAtivos();

    ServicoOutDTO buscarPorId(Long id);

    ServicoOutDTO criar(ServicoInDTO dto);

    ServicoOutDTO atualizar(Long id, ServicoInDTO dto);

    void deletar(Long id);
}