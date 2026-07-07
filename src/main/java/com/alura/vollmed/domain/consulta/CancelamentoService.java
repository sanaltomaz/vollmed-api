package com.alura.vollmed.domain.consulta;

import com.alura.vollmed.domain.consulta.validador.cancelamento.ValidadorCancelamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelamentoService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorCancelamento> validadores;

    public void cancelar(
            DadosCancelamentoConsulta dados,
            Long idConsulta) {

        var consulta = consultaRepository.getReferenceById(idConsulta);

        validadores.forEach(v -> v.validar(dados, idConsulta));

        consulta.cancelar(
                dados.motivo(),
                dados.comentario()
        );
    }
}
