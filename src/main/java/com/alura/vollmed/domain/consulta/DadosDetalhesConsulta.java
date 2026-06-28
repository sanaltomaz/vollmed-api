package com.alura.vollmed.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosDetalhesConsulta(
        Long id,
        Long idMedico,
        String nomeMedico,
        Long idPaciente,
        String nomePaciente,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
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
