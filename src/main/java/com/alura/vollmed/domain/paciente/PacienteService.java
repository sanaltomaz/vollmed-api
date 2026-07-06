    package com.alura.vollmed.domain.paciente;

    import com.alura.vollmed.domain.endereco.DadosEndereco;
    import com.alura.vollmed.domain.endereco.Endereco;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;

    @Service
    public class PacienteService {

        @Autowired
        private PacienteRepository repository;

        public Paciente cadastrar(DadosCadastroPaciente dados) {
            var paciente = criarPaciente(dados);
            repository.save(paciente);

            return paciente;
        }

        public Page<DadosListagemPaciente> listar(Pageable paginacao) {
            return repository.findAllByAtivoTrue(paginacao)
                    .map(DadosListagemPaciente::new);
        }

        public DadosDetalhesPaciente atualizar(DadosAtualizacaoPaciente dados, Long id) {
            var paciente = repository.getReferenceById(id);

            if (dados.nome() != null) {
                paciente.atualizarNome(dados.nome());
            }
            if (dados.telefone() != null) {
                paciente.atualizarTelefone(dados.telefone());
            }
            if (dados.endereco() != null) {
                paciente.atualizarEndereco(converterEndereco(dados.endereco()));
            }

            return new DadosDetalhesPaciente(paciente);
        }

        public void deletar(Long id) {
            var paciente = repository.getReferenceById(id);
            paciente.deletar();
        }

        public Page<DadosListagemPaciente> listarDeletados(Pageable paginacao) {
            return repository.findAllByAtivoFalse(paginacao)
                    .map(DadosListagemPaciente::new);
        }

        public DadosDetalhesPaciente detalhar(Long id) {
            return new DadosDetalhesPaciente(repository.getReferenceById(id));
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

        private Paciente criarPaciente(DadosCadastroPaciente dados) {
            return new Paciente(
                    dados.nome(),
                    dados.email(),
                    dados.telefone(),
                    dados.cpf(),
                    converterEndereco(dados.endereco())
            );
        };

    }
