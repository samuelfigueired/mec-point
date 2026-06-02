package com.mecpoint.veiculo.dto;

import lombok.Data;

@Data
public class VeiculoOutDTO {

    private Long id;
    private String modelo;
    private Integer ano;
    private String marca;
    private String cambio;
    private String placa;

    private Long usuarioId;
    private String usuarioNome;
}