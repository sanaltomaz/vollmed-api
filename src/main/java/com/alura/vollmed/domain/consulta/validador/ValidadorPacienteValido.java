package com.alura.vollmed.domain.consulta.validador;

import com.alura.vollmed.domain.consulta.DadosAgendamentoConsulta;
import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import com.alura.vollmed.domain.paciente.Paciente;
import com.alura.vollmed.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteValido implements ValidadorAgendamento{

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        Paciente paciente = pacienteRepository.findById(dados.idPaciente())
                .orElseThrow(() ->
                        new ValidacaoConsultaException("Paciente não cadastrado")
                );

        if (!paciente.isAtivo()) {
            throw new ValidacaoConsultaException("Paciente inativo");
        }
    }
}
