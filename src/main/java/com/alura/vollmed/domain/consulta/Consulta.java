package com.alura.vollmed.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_medico")
    private Long idMedico;

    @Column(name = "id_paciente")
    private Long idPaciente;

    private LocalDateTime data;

    private boolean status = true;

    @Column(name = "motivo_cancelamento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamento motivoCancelamento;
    private String comentario;

    public Consulta(Long idMedico, Long idPaciente, LocalDateTime data){
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.data = data;
    }

    public Consulta(DadosAgendamentoConsulta dados) {
        this.idMedico = dados.idMedico();
        this.idPaciente = dados.idPaciente();
        this.data = dados.data();
    }

    public void cancelar(MotivoCancelamento motivo, String comentario) {
        this.status = false;
        this.motivoCancelamento = motivo;
        this.comentario = comentario;
    }
}
