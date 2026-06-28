package com.alura.vollmed.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosListagemConsultas(
        Long id,
        Long idMedico,
        Long idPaciente,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
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
