package com.alura.vollmed.domain.consulta.validador.agendamento;

import com.alura.vollmed.domain.consulta.DadosAgendamentoConsulta;
import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamento{

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var domingo = dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioDeAgendamento = dados.data().getHour();

        var inicioConsulta = dados.data();
        var fimConsulta = inicioConsulta.plusHours(1);

        var fechamento = LocalTime.of(19,0)
                .atDate(inicioConsulta.toLocalDate());

        if (domingo) {
            throw new ValidacaoConsultaException("A clínica não funciona aos domingos.");
        }

        if (horarioDeAgendamento < 7) {
            throw new ValidacaoConsultaException("Agendamento de horários somente após as 07:00");
        }

        if (fimConsulta.isAfter(fechamento)) {
            throw new ValidacaoConsultaException(
                    "O último horário disponível para início da consulta é às 18:00."
            );
        }
    }
}
