package com.alura.vollmed.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhesConsulta(
        Long id,
        Long idMedico,
        Long idPaciente,
        LocalDateTime data
) {

    public DadosDetalhesConsulta(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getIdMedico(),
                consulta.getIdPaciente(),
                consulta.getData()
        );
    }
}
