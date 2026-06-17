package com.alura.vollmed.domain.medico;

import com.alura.vollmed.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
