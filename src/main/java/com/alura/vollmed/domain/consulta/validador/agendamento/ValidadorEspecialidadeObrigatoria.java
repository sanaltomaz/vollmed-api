package com.alura.vollmed.domain.consulta.validador.agendamento;

import com.alura.vollmed.domain.consulta.DadosAgendamentoConsulta;
import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorEspecialidadeObrigatoria implements ValidadorAgendamento{

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        if (dados.idMedico() != null) {
            return;
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoConsultaException(
                    "Especialidade é obrigatória quando médico não for escolhido."
            );
        }
    }
}
