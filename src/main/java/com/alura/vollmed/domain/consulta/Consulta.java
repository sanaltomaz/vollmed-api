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

    public Consulta(DadosAgendamentoConsulta dados) {
        this.idMedico = dados.idMedico();
        this.idPaciente = dados.idPaciente();
        this.data = dados.data();
    }

}
