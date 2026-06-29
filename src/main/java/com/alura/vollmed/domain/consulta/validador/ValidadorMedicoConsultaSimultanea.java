package com.alura.vollmed.domain.consulta.validador;

import com.alura.vollmed.domain.consulta.Consulta;
import com.alura.vollmed.domain.consulta.ConsultaRepository;
import com.alura.vollmed.domain.consulta.DadosAgendamentoConsulta;
import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidadorMedicoConsultaSimultanea implements ValidadorAgendamento{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var inicioNova = dados.data();
        var fimNova = inicioNova.plusHours(1);

        List<Consulta> consultas = consultaRepository.findAllByData(dados.idMedico(), dados.data());

        for (var c : consultas) {
            var inicioExistente = c.getData();
            var fimExistente = inicioExistente.plusHours(1);

            boolean conflito =
                    inicioNova.isBefore(fimExistente)
                            && fimNova.isAfter(inicioExistente);

            if (conflito) {
                throw new ValidacaoConsultaException("Médico já possui consulta marcado para esse horário.");
            }
        }
    }
}
