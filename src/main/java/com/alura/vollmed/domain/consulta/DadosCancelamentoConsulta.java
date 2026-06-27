package com.alura.vollmed.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull
        MotivoCancelamento motivo,
        String comentario
) {
}
