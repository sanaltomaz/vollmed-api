package com.alura.vollmed.domain.medico;

import com.alura.vollmed.domain.consulta.Consulta;
import com.alura.vollmed.domain.endereco.DadosEndereco;
import com.alura.vollmed.domain.paciente.DadosCadastroPaciente;
import com.alura.vollmed.domain.paciente.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Testes Medico Repository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    private Paciente paciente;
    private LocalDateTime dataConsulta;

    @BeforeEach
    void setup() {
        paciente = cadastrarPaciente(
                "paciente",
                "paciente@gmail.com",
                "57652579073"
        );

        dataConsulta = proximaSegundaAs10Horas();
    }

    @Test
    @DisplayName("Devolver Null quando não houver médicos disponiveis")
    void deveRetornarNullQuandoNaoExistirMedicoDisponivel() {
        var medicoLivre = buscarMedico();

        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Devolve Medico quando houver médico disponivel")
    void deveRetornarMedicoQuandoExistirDisponibilidade() {
        var medico = cadastrarMedicoPadrao();
        var medicoLivre = buscarMedico();

        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Devolver Null quando médico estiver ocupado")
    void deveRetornarNullQuandoMedicoEstiverOcupado() {
        var medico = cadastrarMedicoPadrao();

        cadastrarConsulta(medico, paciente, dataConsulta);

        var medicoLivre = buscarMedico();

        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deve retornar outro médico disponível quando o primeiro estiver ocupado")
    void deveRetornarOutroMedicoDisponivelQuandoPrimeiroEstiverOcupado() {
        var medico = cadastrarMedicoPadrao();

        cadastrarConsulta(medico, paciente, dataConsulta);

        var outroMedico = cadastrarMedico(
                "outro medico",
                "outro.medico@vollmed.com",
                "890919",
                Especialidade.CARDIOLOGIA);

        var medicoLivre = buscarMedico();

        assertThat(medicoLivre).isEqualTo(outroMedico);
    }

    private LocalDateTime proximaSegundaAs10Horas() {
        return LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10)
                .withMinute(0);
    }

    private Medico buscarMedico() {
        return medicoRepository
                .escolherMedicoAleatorioLivreNaData(
                        Especialidade.CARDIOLOGIA,
                        dataConsulta
                );
    }

    private Medico cadastrarMedicoPadrao() {
        return cadastrarMedico(
                "medico",
                "medico@vollmed.com",
                "687566",
                Especialidade.CARDIOLOGIA
        );
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(medico.getId(), paciente.getId(), data));
        em.flush();
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        em.flush();
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        em.flush();
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                "DF",
                "79874015"
        );
    }
}