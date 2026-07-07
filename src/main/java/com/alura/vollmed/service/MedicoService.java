package com.alura.vollmed.service;

import com.alura.vollmed.domain.endereco.DadosEndereco;
import com.alura.vollmed.domain.endereco.Endereco;
import com.alura.vollmed.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    public Medico cadastrar(DadosCadastroMedico dados) {
        var medico = criarMedico(dados);
        repository.save(medico);

        return medico;
    }

    public Page<DadosListagemMedico> listar(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedico::new);
    }

    public DadosDetalhesMedico atualizar(
            DadosAtualizacaoMedico dados,
            Long id
    ) {

        var medico = repository.getReferenceById(id);

        if (dados.nome() != null) {
            medico.atualizarNome(dados.nome());
        }

        if (dados.telefone() != null) {
            medico.atualizarTelefone(dados.telefone());
        }

        if (dados.endereco() != null) {
            medico.atualizarEndereco(
                    converterEndereco(dados.endereco()
                    ));
        }

        return new DadosDetalhesMedico(medico);
    }

    public void deletar(Long id) {
        var medico = repository.getReferenceById(id);
        medico.deletar();
    }

    public Page<DadosListagemMedico> listarDeletados(Pageable paginacao) {
        return repository.findAllByAtivoFalse(paginacao)
                .map(DadosListagemMedico::new);
    }

    public DadosDetalhesMedico detalhar(Long id) {
        return new DadosDetalhesMedico(
                repository.getReferenceById(id)
        );
    }

    private Medico criarMedico(DadosCadastroMedico dados) {
        return new Medico(
                dados.nome(),
                dados.email(),
                dados.telefone(),
                dados.crm(),
                dados.especialidade(),
                converterEndereco(dados.endereco())
        );
    }

    private Endereco converterEndereco(DadosEndereco dadosEndereco) {
        return new Endereco(
                dadosEndereco.logradouro(),
                dadosEndereco.numero(),
                dadosEndereco.complemento(),
                dadosEndereco.bairro(),
                dadosEndereco.cidade(),
                dadosEndereco.uf(),
                dadosEndereco.cep()
        );
    }
}
