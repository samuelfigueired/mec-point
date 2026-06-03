package com.mecpoint.agendamento.entities;

import com.mecpoint.agendamento.entities.enums.StatusAgendamento;
import com.mecpoint.user.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TB_AGENDAMENTO_EVENTO")
public class AgendamentoEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AGENDAMENTO", nullable = false)
    private Agendamento agendamento;

    @Column(name = "TITULO", nullable = false, length = 150)
    private String titulo;

    @Column(name = "DESCRICAO", length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 30)
    private StatusAgendamento status;

    @Column(name = "DATA_EVENTO", nullable = false)
    private LocalDateTime dataEvento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CRIADO_POR")
    private User criadoPor;
}