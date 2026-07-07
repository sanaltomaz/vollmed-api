package com.alura.vollmed.domain.consulta;

import com.alura.vollmed.domain.consulta.validador.agendamento.ValidadorAgendamento;
import com.alura.vollmed.domain.medico.Medico;
import com.alura.vollmed.domain.medico.MedicoRepository;
import com.alura.vollmed.domain.paciente.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private List<ValidadorAgendamento> validadores;

    public DadosDetalhesConsulta agendar(@Valid DadosAgendamentoConsulta dados) {

        validadores.forEach(v -> v.validar(dados));

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

        return new DadosDetalhesConsulta(
                consulta,
                medico.getNome(),
                paciente.getNome()
        );
    }

    private Medico escolherMedico(@Valid DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
