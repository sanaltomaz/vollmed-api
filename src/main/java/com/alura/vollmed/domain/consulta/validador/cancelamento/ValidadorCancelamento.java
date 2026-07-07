package com.alura.vollmed.domain.consulta.validador.cancelamento;

import com.alura.vollmed.domain.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamento {

    void validar(DadosCancelamentoConsulta dados, Long id);
}
