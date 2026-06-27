package com.alura.vollmed.domain.consulta;

import java.time.LocalDateTime;

public record DadosListagemConsultas(
        Long id,
        Long idMedico,
        Long idPaciente,
        LocalDateTime data
) {
    public DadosListagemConsultas(Consulta consulta){
        this(
                consulta.getId(),
                consulta.getIdMedico(),
                consulta.getIdPaciente(),
                consulta.getData()
        );
    }
}
