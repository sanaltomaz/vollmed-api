package com.alura.vollmed.controller;

import com.alura.vollmed.domain.consulta.AgendamentoService;
import com.alura.vollmed.domain.consulta.ConsultaRepository;
import com.alura.vollmed.domain.consulta.DadosAgendamentoConsulta;
import com.alura.vollmed.domain.consulta.DadosListagemConsultas;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity agendarConsulta(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        agendamentoService.agendar(dados);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemConsultas>> visualizarConsultas(
            @PageableDefault(size = 5, sort = {"data"})Pageable paginacao
            ) {

        var page = repository.findAll(paginacao)
                .map(DadosListagemConsultas::new);

        return ResponseEntity.ok(page);
    }
}
