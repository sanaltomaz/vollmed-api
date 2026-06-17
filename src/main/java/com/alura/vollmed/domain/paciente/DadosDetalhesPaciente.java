package com.alura.vollmed.domain.paciente;

import com.alura.vollmed.domain.endereco.Endereco;

public record DadosDetalhesPaciente(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco
) {
    public DadosDetalhesPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getEndereco()
        );
    }
}
