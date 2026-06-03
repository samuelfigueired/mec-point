package com.mecpoint.agendamento.entities;

import com.mecpoint.agendamento.entities.enums.StatusAgendamento;
import com.mecpoint.servico.entities.Servico;
import com.mecpoint.user.entities.User;
import com.mecpoint.veiculo.entities.Veiculo;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TB_AGENDAMENTO")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDAMENTO")
    private Long id;

    @Column(name = "CLIENTE")
    private String cliente;

    @Column(name = "DATA_HORA")
    private LocalDateTime dataHora;

    @Column(name = "NUMERO_AGD")
    private String numeroAgd;

    @Column(name = "SERVICO")
    private String servico;

    @Column(name = "DESCRICAO", length = 1000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 30)
    private StatusAgendamento status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VEICULO")
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MECANICO")
    private User mecanico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SERVICO")
    private Servico servicoRef;
}