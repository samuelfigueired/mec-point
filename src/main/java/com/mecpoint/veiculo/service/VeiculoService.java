package com.mecpoint.veiculo.service;

import com.mecpoint.veiculo.dto.VeiculoInDTO;
import com.mecpoint.veiculo.dto.VeiculoOutDTO;
import com.mecpoint.veiculo.entities.Veiculo;

import java.util.List;

public interface VeiculoService {

    List<VeiculoOutDTO> listarTodos();

    List<VeiculoOutDTO> listarMeusVeiculos();

    List<VeiculoOutDTO> listarPorUsuario(Long usuarioId);

    VeiculoOutDTO buscarPorId(Long id);

    Veiculo buscarEntidadePorId(Long id);

    VeiculoOutDTO criarVeiculo(VeiculoInDTO dto);

    VeiculoOutDTO atualizarVeiculo(Long id, VeiculoInDTO dto);

    void deletarVeiculo(Long id);
}