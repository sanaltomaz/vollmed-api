package com.alura.vollmed.controller;

import com.alura.vollmed.domain.paciente.DadosAtualizacaoPaciente;
import com.alura.vollmed.domain.paciente.DadosCadastroPaciente;
import com.alura.vollmed.domain.paciente.DadosDetalhesPaciente;
import com.alura.vollmed.domain.paciente.DadosListagemPaciente;
import com.alura.vollmed.service.PacienteService;
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
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhesPaciente> cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder) {

        var paciente = service.cadastrar(dados);

        var uri = uriBuilder.path("/pacientes/{id}")
                .buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri)
                .body(new DadosDetalhesPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(
            @PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {

        return ResponseEntity.ok(service.listar(paginacao));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhesPaciente> atualizar(
            @RequestBody @Valid DadosAtualizacaoPaciente dados,
            @PathVariable Long id) {

        return ResponseEntity.ok(service.atualizar(dados, id));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("deletados")
    public ResponseEntity<Page<DadosListagemPaciente>> listarDeletados(
            @PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {

        return ResponseEntity.ok(service.listarDeletados(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhesPaciente> detalhar(@PathVariable Long id) {

        return ResponseEntity.ok(service.detalhar(id));
    }
}
