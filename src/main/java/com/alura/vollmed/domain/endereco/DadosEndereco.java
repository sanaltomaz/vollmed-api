package com.alura.vollmed.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank String logradouro,

        // Não obrigatorios para registro.
        String numero,
        String complemento,

        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank String uf,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep
) {
}
