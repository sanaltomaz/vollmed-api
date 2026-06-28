package com.alura.vollmed.domain.consulta;

import com.alura.vollmed.domain.medico.Medico;
import com.alura.vollmed.domain.medico.MedicoRepository;
import com.alura.vollmed.domain.paciente.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public DadosDetalhesConsulta agendar(@Valid DadosAgendamentoConsulta dados) {

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null) {
            throw new ValidacaoConsultaException("Sem médico disponível no momento.");
        }

        var consulta = new Consulta(
                medico.getId(),
                paciente.getId(),
                dados.data()
        );

        consultaRepository.save(consulta);

        var nomeMedico = medico.getNome();
        var nomePaciente = paciente.getNome();

        return new DadosDetalhesConsulta(consulta, nomeMedico, nomePaciente);
    }

    private Medico escolherMedico(@Valid DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoConsultaException("Especialidade é obrigatória quando médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
