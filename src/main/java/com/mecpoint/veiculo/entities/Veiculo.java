package com.mecpoint.veiculo.entities;

import com.mecpoint.user.entities.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TB_VEICULO")
@Data
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VEICULO")
    private Long id;

    @Column(name = "MODELO", nullable = false, length = 80)
    private String modelo;

    @Column(name = "ANO", nullable = false)
    private Integer ano;

    @Column(name = "MARCA", nullable = false, length = 80)
    private String marca;

    @Column(name = "CAMBIO", nullable = false, length = 30)
    private String cambio;

    @Column(name = "PLACA", nullable = false, unique = true, length = 10)
    private String placa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private User usuario;
}