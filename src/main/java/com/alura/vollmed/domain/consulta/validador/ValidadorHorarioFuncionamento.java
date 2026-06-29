package com.alura.vollmed.domain.consulta.validador;

import com.alura.vollmed.domain.consulta.DadosAgendamentoConsulta;
import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamento{

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var domingo = dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioDeAgendamento = dados.data().getHour();

        if (domingo) {
            throw new ValidacaoConsultaException("A clínica não funciona aos domingos.");
        }

        if (horarioDeAgendamento < 7) {
            throw new ValidacaoConsultaException("Agendamento de horários somente após as 07:00");
        }

        if (horarioDeAgendamento > 18) {
            throw new ValidacaoConsultaException("Agendamento de horários somente até as 18:00");
        }
    }
}
