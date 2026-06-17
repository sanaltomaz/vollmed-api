package com.alura.vollmed.controller;

import com.alura.vollmed.domain.medico.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoRepository repository;

    public MedicoController(MedicoRepository repository){
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(
            @RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder){

        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}")
                .buildAndExpand(medico.getId()).toUri();


        return ResponseEntity.created(uri)
                .body(new DadosDetalhesMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(
            @PageableDefault(size=5, sort={"nome"}) Pageable paginacao) {

        var page = repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedico::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(
            @RequestBody @Valid DadosAtualizacaoMedico dados,
            @PathVariable Long id){

        var medico = repository.getReferenceById(id);
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhesMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.deletar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deletados")
    public ResponseEntity<Page<DadosListagemMedico>> listarDeletados(
            @PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoFalse(paginacao)
                .map(DadosListagemMedico::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhesMedico(medico));
    }

}
