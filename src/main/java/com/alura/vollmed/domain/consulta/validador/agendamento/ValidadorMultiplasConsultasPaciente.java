package com.alura.vollmed.domain.consulta.validador.agendamento;

import com.alura.vollmed.domain.consulta.ConsultaRepository;
import com.alura.vollmed.domain.consulta.DadosAgendamentoConsulta;
import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ValidadorMultiplasConsultasPaciente implements ValidadorAgendamento {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        var data = dados.data().toLocalDate();

        var inicioDoDia = data.atStartOfDay();
        var fimDoDia = data.atTime(LocalTime.MAX);

        var pacientePossuiConsulta = consultaRepository
                .existsByPacienteNoIntervalo(
                        dados.idPaciente(),
                        inicioDoDia,
                        fimDoDia
                );

        if (pacientePossuiConsulta) {
            throw new ValidacaoConsultaException(
                    "Paciente já possui consulta agendada neste dia."
            );
        }
    }
}
