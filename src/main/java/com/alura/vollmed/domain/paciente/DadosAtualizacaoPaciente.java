package com.alura.vollmed.domain.paciente;

import com.alura.vollmed.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
