package com.alura.vollmed.controller;

import com.alura.vollmed.domain.medico.DadosAtualizacaoMedico;
import com.alura.vollmed.domain.medico.DadosCadastroMedico;
import com.alura.vollmed.domain.medico.DadosDetalhesMedico;
import com.alura.vollmed.domain.medico.DadosListagemMedico;
import com.alura.vollmed.service.MedicoService;
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
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhesMedico> cadastrar(
            @RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder){

        var medico = service.cadastrar(dados);

        var uri = uriBuilder.path("/medicos/{id}")
                .buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri)
                .body(service.detalhar(medico.getId()));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(
            @PageableDefault(size=5, sort={"nome"}) Pageable paginacao) {

        return ResponseEntity.ok(
                service.listar(paginacao)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhesMedico> atualizar(
            @RequestBody @Valid DadosAtualizacaoMedico dados,
            @PathVariable Long id){

        return ResponseEntity.ok(
                service.atualizar(dados, id)
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id){

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deletados")
    public ResponseEntity<Page<DadosListagemMedico>> listarDeletados(
            @PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {

        return ResponseEntity.ok(
                service.listarDeletados(paginacao)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhesMedico> detalhar(@PathVariable Long id){

        return ResponseEntity.ok(service.detalhar(id));
    }

}
