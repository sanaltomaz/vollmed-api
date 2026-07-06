package com.alura.vollmed.domain.paciente;

import com.alura.vollmed.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;

public record DadosAtualizacaoPaciente(
        String nome,
        String telefone,

        @Valid
        DadosEndereco endereco
) {
}
