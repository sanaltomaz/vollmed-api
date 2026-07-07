package com.alura.vollmed.domain.consulta.validador.cancelamento;

import com.alura.vollmed.domain.consulta.ConsultaRepository;
import com.alura.vollmed.domain.consulta.DadosCancelamentoConsulta;
import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorAntecendenciaCancelamento implements ValidadorCancelamento{

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados, Long id) {
        var consulta = repository.getReferenceById(id);
        var dataConsulta = consulta.getData();
        var dataMinima = LocalDateTime.now().plusHours(24);

        if (dataConsulta.isBefore(dataMinima)) {
            throw new ValidacaoConsultaException(
                    "Cancelamento deve ter antecedência de 24 horas"
            );
        }
    }
}
