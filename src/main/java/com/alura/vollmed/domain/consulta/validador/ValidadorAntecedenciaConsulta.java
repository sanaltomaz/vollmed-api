package com.alura.vollmed.domain.consulta.validador;

import com.alura.vollmed.domain.consulta.DadosAgendamentoConsulta;
import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorAntecedenciaConsulta implements ValidadorAgendamento{

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var horarioMinimo = LocalDateTime.now().plusMinutes(30);

        if (dados.data().isBefore(horarioMinimo)){
            throw new ValidacaoConsultaException(
                    "Horário deve ser agendado com mínimo de 30 minutos de antecedência");
        }
    }
}
