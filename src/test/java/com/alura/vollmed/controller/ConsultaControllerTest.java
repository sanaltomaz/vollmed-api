package com.alura.vollmed.controller;

import com.alura.vollmed.domain.consulta.*;
import com.alura.vollmed.domain.medico.Especialidade;
import com.alura.vollmed.domain.usuario.UsuarioRepository;
import com.alura.vollmed.infra.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AgendamentoService agendamentoService;

    @MockitoBean
    private CancelamentoService cancelamentoService;

    @MockitoBean
    private ConsultaRepository repository;

    // Não remover
    @MockitoBean
    private TokenService tokenService;

    // Não remover
    @MockitoBean
    private UsuarioRepository usuarioRepository;


    @Test
    @DisplayName("Agendar deve retornar 201 Created quando Sucesso")
    void deveRetornarCreatedQuandoAgendamentoForRealizado() throws Exception {
        var dados = dadosAgendamentoPadrao();

        when(agendamentoService.agendar(any()))
                .thenReturn(dadosDetalhesPadrao());

        mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dados)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Agendar deve retornar 400 Bad Request quando Dados Inválidos")
    void deveRetornarBadRequestQuandoDadosInvalidos() throws Exception {
        var dados = new DadosAgendamentoConsulta(
                null,
                null,
                null,
                null
        );

        mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dados))
                ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Agenda deve retornar 400 Bad Request quando JSON Inválido")
    void deveRetornarBadRequestQuandoJsonForInvalido() throws Exception {
        var json = """
                {
                    "idPaciente":
                }
                """;

        mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Visualizar Consultas deve retornar 200 Ok")
    void deveRetornarOkQuandoVisualizarConsulta() throws Exception {
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mvc.perform(get("/consultas")
            ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Visualizar Consultas deve retornar JSON paginado")
    void deveRetornarJsonPaginado() throws Exception {
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mvc.perform(get("/consultas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.totalElements").exists())
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.size").exists());
    }

    @Test
    @DisplayName("Cancelar deve retornar 204 No Content")
    void deveRetornarNoContentQuandoConsultaForCancelada() throws Exception {
        mvc.perform(delete("/consultas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dadosCancelamentoPadrao()))
                    ).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Cancelar deve retornar 400 Bad Request quando Dados Inválidos")
    void deveRetornarBadRequestQuandoDadosCancelamentoForemInvalidos() throws Exception {
        var dados = new DadosCancelamentoConsulta(
                null,
                null
        );

        mvc.perform(delete("/consultas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dados))
                ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Cancelar deve retornar 400 Bad Request quando JSON Inválido")
    void deveRetornarBadRequestQuandoJsonCancelamentoForInvalido() throws Exception {
        String json = """
            {
                "motivo":
            }
            """;

        mvc.perform(delete("/consultas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isBadRequest());
    }

    private DadosAgendamentoConsulta dadosAgendamentoPadrao() {
        return new DadosAgendamentoConsulta(
                1L,
                2L,
                LocalDateTime.now().plusDays(1),
                Especialidade.CARDIOLOGIA
        );
    }

    private DadosDetalhesConsulta dadosDetalhesPadrao() {
        return new DadosDetalhesConsulta(
                1L,
                1L,
                "Dr. João",
                2L,
                "Edson Castro",
                LocalDateTime.now().plusDays(1)
        );
    }

    private DadosCancelamentoConsulta dadosCancelamentoPadrao() {
        return new DadosCancelamentoConsulta(
                MotivoCancelamento.PACIENTE_DESISTIU,
                "Comentário"
        );
    }
}