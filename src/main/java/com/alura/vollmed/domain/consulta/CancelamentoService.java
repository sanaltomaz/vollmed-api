package com.alura.vollmed.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelamentoService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void cancelar(
            DadosCancelamentoConsulta dados,
            Long idConsulta) {

        var consulta = consultaRepository.getReferenceById(idConsulta);

        consulta.cancelar(
                dados.motivo(),
                dados.comentario()
        );
    }
}
