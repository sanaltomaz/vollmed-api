package com.alura.vollmed.domain.medico;

import com.alura.vollmed.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;

public record DadosAtualizacaoMedico(
        String nome,
        String telefone,

        @Valid
        DadosEndereco endereco
) {
}
