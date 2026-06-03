package com.mecpoint.servico.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServicoInDTO {

    @NotBlank(message = "O nome do serviço é obrigatório.")
    private String nome;

    private String descricao;

    @NotNull(message = "O valor do serviço é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor do serviço deve ser maior que zero.")
    private BigDecimal valor;

    private String categoria;

    private Boolean ativo;
}