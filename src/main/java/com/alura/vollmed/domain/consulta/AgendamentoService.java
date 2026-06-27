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

    public void agendar(@Valid DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoConsultaException("Id do paciente informado não existe!");
        }

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null) {
            throw new ValidacaoConsultaException("Sem médico disponível no momento.");
        }

        var consulta = new Consulta(null, medico.getId(), paciente.getId(), dados.data(), true, null, "");

        consultaRepository.save(consulta);
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
