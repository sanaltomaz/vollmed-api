package com.alura.vollmed.domain.consulta;

import com.alura.vollmed.domain.endereco.Endereco;
import com.alura.vollmed.domain.medico.Especialidade;
import com.alura.vollmed.domain.medico.Medico;
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
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Testes Consulta Repository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ConsultaRepositoryTest {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private TestEntityManager em;

    private Medico medico;
    private Paciente paciente;
    private LocalDateTime dataConsulta;
    private LocalDateTime inicioDoDia;
    private LocalDateTime fimDoDia;

    @BeforeEach
    void setup() {
        dataConsulta = proximaSegundaAs10Horas();
        inicioDoDia = dataConsulta.toLocalDate().atStartOfDay();
        fimDoDia = dataConsulta.toLocalDate().atTime(LocalTime.MAX);

        paciente = cadastrarPaciente(pacientePadrao());
        medico = cadastrarMedico(medicoPadrao());
    }

    @Test
    @DisplayName("Deve retornar True se Existir Paciente possui consulta no dia")
    void deveRetornarTrueQuandoExistirNoIntervalo() {

        cadastrarConsulta(medico, paciente, dataConsulta);

        var pacientePossuiConsulta = buscarConsultaDePaciente(paciente.getId());

        assertThat(pacientePossuiConsulta).isEqualTo(Boolean.TRUE);
    }

    @Test
    @DisplayName("Deve retornar FALSE quando NÃO existir consulta do paciente na data")
    void deveRetornarFalseQuandoNaoExistirNoIntervalo() {

        var pacientePossuiConsulta = buscarConsultaDePaciente(paciente.getId());

        assertThat(pacientePossuiConsulta).isEqualTo(Boolean.FALSE);
    }

    @Test
    @DisplayName("Deve retornar FALSE quando a consulta pertencer a outro paciente")
    void deveRetornarFalseQuandoExistirConsultaDeOutroPaciente() {

        var outroPaciente = cadastrarPaciente(pacientePadrao("Outro Paciente"));

        cadastrarConsulta(medico, outroPaciente, dataConsulta);
        var pacientePossuiConsulta = buscarConsultaDePaciente(paciente.getId());

        assertThat(pacientePossuiConsulta).isEqualTo(Boolean.FALSE);
    }

    @Test
    @DisplayName("Deve retornar TRUE quando a consulta é no Inicio do Intervalo")
    void deveRetornarTrueQuandoConsultaInicioDoIntervalo() {
        var novaDataConsulta = dataConsulta.toLocalDate().atStartOfDay();
        cadastrarConsulta(medico, paciente, novaDataConsulta);

        var pacientePossuiConsulta = buscarConsultaDePaciente(paciente.getId());

        assertThat(pacientePossuiConsulta).isEqualTo(Boolean.TRUE);
    }

    @Test
    @DisplayName("Deve retornar TRUE quando a consulta é no Final do Intervalo")
    void deveRetornarTrueQuandoConsultaFinalDoIntervalo() {
        var novaDataConsulta = dataConsulta.toLocalDate().atTime(LocalTime.MAX);
        cadastrarConsulta(medico, paciente, novaDataConsulta);

        var pacientePossuiConsulta = buscarConsultaDePaciente(paciente.getId());

        assertThat(pacientePossuiConsulta).isEqualTo(Boolean.TRUE);
    }

    private Boolean buscarConsultaDePaciente(Long idPaciente) {
        return consultaRepository
                .existsByPacienteNoIntervalo(
                        idPaciente,
                        inicioDoDia,
                        fimDoDia
                );
    }

    private LocalDateTime proximaSegundaAs10Horas() {
        return LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10)
                .withMinute(0);
    }

    private Paciente cadastrarPaciente(Paciente paciente) {
        em.persist(paciente);
        em.flush();
        return paciente;
    }

    private Medico cadastrarMedico(Medico medico) {
        em.persist(medico);
        em.flush();
        return medico;
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(medico.getId(), paciente.getId(), data));
        em.flush();
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

    private Paciente pacientePadrao(String nome) {
        return new Paciente(
                nome,
                "outropaciente@gmail.com",
                "17912345678",
                "10020230012",
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