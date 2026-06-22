package com.alura.vollmed.controller;

import com.alura.vollmed.domain.paciente.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteRepository repository;

    public PacienteController(PacienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(
            @RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder) {

        var paciente = new Paciente(dados);
        repository.save(paciente);

        var uri = uriBuilder.path("/pacientes/{id}")
                .buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri)
                .body(new DadosDetalhesPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(
            @PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {

        var page = repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(
            @RequestBody @Valid DadosAtualizacaoPaciente dados,
            @PathVariable Long id) {

        var paciente = repository.getReferenceById(id);
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhesPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.deletar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("deletados")
    public ResponseEntity<Page<DadosListagemPaciente>> listarDeletados(
            @PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoFalse(paginacao)
                .map(DadosListagemPaciente::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhesPaciente(paciente));
    }
}
