package com.alura.vollmed.domain.medico;

import com.alura.vollmed.domain.consulta.Consulta;
import com.alura.vollmed.domain.endereco.Endereco;
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
    private Medico medico;
    private LocalDateTime dataConsulta;

    @BeforeEach
    void setup() {
        dataConsulta = proximaSegundaAs10Horas();

        medico = cadastrarMedico();
        paciente = cadastrarPaciente();
    }

    @Test
    @DisplayName("Devolver Null quando não houver médicos disponiveis")
    void deveRetornarNullQuandoNaoExistirMedicoDisponivel() {
        cadastrarConsulta(medico, paciente, dataConsulta);

        var medicoLivre = buscarMedico();

        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Devolve Medico quando houver médico disponivel")
    void deveRetornarMedicoQuandoExistirDisponibilidade() {
        var medicoLivre = buscarMedico();

        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("Devolver Null quando médico estiver ocupado")
    void deveRetornarNullQuandoMedicoEstiverOcupado() {

        cadastrarConsulta(medico, paciente, dataConsulta);

        var medicoLivre = buscarMedico();

        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deve retornar outro médico disponível quando o primeiro estiver ocupado")
    void deveRetornarOutroMedicoDisponivelQuandoPrimeiroEstiverOcupado() {

        var outroMedico = cadastrarMedico("Outro Medico");

        cadastrarConsulta(medico, paciente, dataConsulta);

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

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(medico.getId(), paciente.getId(), data));
        em.flush();
    }

    private Medico cadastrarMedico() {
        var medico = medicoPadrao();
        em.persist(medico);
        em.flush();
        return medico;
    }

    private Medico cadastrarMedico(String nome) {
        var medico = medicoPadrao(nome);
        em.persist(medico);
        em.flush();
        return medico;
    }

    private Paciente cadastrarPaciente() {
        var paciente = pacientePadrao();
        em.persist(paciente);
        em.flush();
        return paciente;
    }

    private Paciente pacientePadrao() {
        return new Paciente(
                "Paciente",
                "paciente@gmail.com",
                "17912345678",
                "00100200312",
                enderecoPadrao()
        );
    }

    private Medico medicoPadrao(String nome) {
        return new Medico(
                nome,
                "outromedico@vollmed.com",
                "17987654321",
                "100200",
                Especialidade.CARDIOLOGIA,
                enderecoPadrao()
        );
    }

    private Medico medicoPadrao() {
        return new Medico(
                "Medico",
                "medico@vollmed.com",
                "17912345678",
                "001002",
                Especialidade.CARDIOLOGIA,
                enderecoPadrao()
        );
    }

    private Endereco enderecoPadrao() {
        return new Endereco(
                "Rua XPTO",
                "100",
                "Casa",
                "Centro",
                "Brasília",
                "DF",
                "79874015"
        );
    }
}