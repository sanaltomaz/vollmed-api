package com.alura.vollmed.domain.consulta.validador.agendamento;

import com.alura.vollmed.domain.consulta.DadosAgendamentoConsulta;
import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import com.alura.vollmed.domain.medico.Medico;
import com.alura.vollmed.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoInformado implements ValidadorAgendamento{

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) {
            return;
        }

        Medico medico = medicoRepository.findById(dados.idMedico())
                .orElseThrow(() ->
                        new ValidacaoConsultaException("Médico não cadastrado."));

        if(!medico.isAtivo()) {
            throw new ValidacaoConsultaException("Médico inativo");
        }
    }
}
