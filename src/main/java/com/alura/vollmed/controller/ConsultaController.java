package com.alura.vollmed.controller;

import com.alura.vollmed.domain.consulta.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity agendarConsulta(
            @RequestBody @Valid DadosAgendamentoConsulta dados,
            UriComponentsBuilder uriBuilder) {

        var consulta = new Consulta(dados);
        agendamentoService.agendar(dados);

        var uri = uriBuilder.path("/consultas/{id}")
                .buildAndExpand(consulta.getId()).toUri();

        return ResponseEntity.created(uri)
                .body(new DadosDetalhesConsulta(consulta));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemConsultas>> visualizarConsultas(
            @PageableDefault(size = 5, sort = {"data"})Pageable paginacao
            ) {

        var page = repository.findAll(paginacao)
                .map(DadosListagemConsultas::new);

        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelarConsulta(
            @RequestBody @Valid DadosCancelamentoConsulta dados,
            @PathVariable Long id) {
        var consulta = repository.getReferenceById(id);
        consulta.cancelar(dados);

        return ResponseEntity.noContent().build();
    }
}
