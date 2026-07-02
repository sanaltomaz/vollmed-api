package com.alura.vollmed.domain.consulta;

import com.alura.vollmed.domain.endereco.DadosEndereco;
import com.alura.vollmed.domain.medico.DadosCadastroMedico;
import com.alura.vollmed.domain.medico.Especialidade;
import com.alura.vollmed.domain.medico.Medico;
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

        paciente = cadastrarPaciente(
                "paciente",
                "paciente@gmail.com",
                "12079127010"
        );

        medico = cadastrarMedico(
                "medico",
                "medico@vollmed.com",
                "201897",
                Especialidade.CARDIOLOGIA
        );
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
        var outroPaciente = cadastrarPaciente(
                "outro paciente",
                "outro.paciente@gmail.com",
                "15786590130"
        );

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

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        em.flush();
        return paciente;
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        em.flush();
        return medico;
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(medico.getId(), paciente.getId(), data));
        em.flush();
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