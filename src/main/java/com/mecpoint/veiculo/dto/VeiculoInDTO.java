package com.mecpoint.veiculo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VeiculoInDTO {

    @NotBlank(message = "O modelo do veículo é obrigatório.")
    private String modelo;

    @NotNull(message = "O ano do veículo é obrigatório.")
    @Min(value = 1900, message = "O ano do veículo deve ser maior ou igual a 1900.")
    @Max(value = 2100, message = "O ano do veículo deve ser válido.")
    private Integer ano;

    @NotBlank(message = "A marca do veículo é obrigatória.")
    private String marca;

    @NotBlank(message = "O câmbio do veículo é obrigatório.")
    private String cambio;

    @NotBlank(message = "A placa do veículo é obrigatória.")
    private String placa;

    @NotNull(message = "O usuário do veículo é obrigatório.")
    private Long usuarioId;
}