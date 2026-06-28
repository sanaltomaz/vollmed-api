package com.alura.vollmed.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhesConsulta(
        Long id,
        Long idMedico,
        String nomeMedico,
        Long idPaciente,
        String nomePaciente,
        LocalDateTime data
) {

    public DadosDetalhesConsulta(Consulta consulta, String nomeMedico, String nomePaciente) {
        this(
                consulta.getId(),
                consulta.getIdMedico(),
                nomeMedico,
                consulta.getIdPaciente(),
                nomePaciente,
                consulta.getData()
        );
    }
}
