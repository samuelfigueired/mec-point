package com.mecpoint.veiculo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VeiculoInDTO {

    @NotBlank(message = "O modelo é obrigatório.")
    private String modelo;

    @NotNull(message = "O ano é obrigatório.")
    private Integer ano;

    @NotBlank(message = "A marca é obrigatória.")
    private String marca;

    @NotBlank(message = "O câmbio é obrigatório.")
    private String cambio;

    @NotBlank(message = "A placa é obrigatória.")
    private String placa;

    private Long usuarioId;
}