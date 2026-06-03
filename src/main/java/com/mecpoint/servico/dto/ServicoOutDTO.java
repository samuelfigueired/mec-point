package com.mecpoint.servico.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServicoOutDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String categoria;
    private Boolean ativo;
}