package com.alura.vollmed.controller;

import com.alura.vollmed.domain.endereco.DadosEndereco;
import com.alura.vollmed.domain.endereco.Endereco;
import com.alura.vollmed.domain.paciente.*;
import com.alura.vollmed.domain.usuario.UsuarioRepository;
import com.alura.vollmed.infra.security.TokenService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PacienteController.class)
@AutoConfigureMockMvc(addFilters = false)
class PacienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PacienteService service;

    // Não remover
    @MockitoBean
    private TokenService tokenService;

    // Não remover
    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Cadastrar deve retornar 201 Created quando Sucesso")
    void deveRetornarCreatedQuandoCadastradoComSucesso() throws Exception {

        var dados = dadosCadastroPadrao();

        when(service.cadastrar(any(DadosCadastroPaciente.class)))
                .thenReturn(pacientePadrao());

        mvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dados)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar deve retornar 400 Bad Request quando Dados Inválidos")
    void deveRetornarBadRequestQuandoDadosInvalidos() throws Exception {

        var dadosInvalidos = new DadosCadastroPaciente(
                "Paciente",
                "paciente@gmail.com",
                "10",
                "5010",
                dadosEnderecoPadrao()
        );

        mvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dadosInvalidos)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Cadastrar deve retornar 400 Bad Request quando JSON Inválido")
    void deveRetornarBadRequestQuandoJsonForInvalido() throws Exception {

        var jsonInvalido = """
            {
                "nome":
            }
            """;

        mvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Listar deve retornar 200 Ok quando disparado")
    void deveRetornarOkQuandoListar() throws Exception {

        when(service.listar(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mvc.perform(get("/pacientes"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Listar deve retornar JSON paginado")
    void deveRetornarJsonPaginado() throws Exception{
        when(service.listar(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mvc.perform(get("/pacientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.totalElements").exists())
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.size").exists());
    }

    @Test
    @DisplayName("Detalhar deve retornar 200 Ok se existir Id")
    void deveRetornarOkQuandoExistirIdPaciente() throws Exception {
        when(service.detalhar(1L))
                .thenReturn(new DadosDetalhesPaciente(pacientePadrao()));

        mvc.perform(get("/pacientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Edson Castro"))
                .andExpect(jsonPath("$.email").value("edson.castro@email.com"));
    }

    @Test
    @DisplayName("Detalhar deve retornar 404 EntityNotFoundException se não existir Id")
    void deveRetornarEntityNotFoundExceptionQuandoNaoExistirIdPacienteParaDetalhar() throws Exception {
        when(service.detalhar(99L))
                .thenThrow(EntityNotFoundException.class);

        mvc.perform(get("/pacientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Atualizar deve retornar 200 Ok quando Dados e Id corretos")
    void deveRetornarOkQuandoAtualizarPaciente() throws Exception {

        when(service.atualizar(
                any(DadosAtualizacaoPaciente.class),
                any(Long.class)
                )).thenReturn(
                        new DadosDetalhesPaciente(pacienteAtualizado()));

        mvc.perform(put("/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dadosAtualizacaoPadrao()))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Nome"));
    }

    @Test
    @DisplayName("Atualizar deve retornar 400 Body Invalido quando JSON de Dados errado")
    void deveRetornarBodyInvalidoQuandoDadosParaAtualizarForemInvalidos() throws Exception {

        String jsonInvalido = """
                {
                    "nome": 
                }
                """;

        mvc.perform(put("/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido)
                ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Atualizar deve retornar 404 EntityNotFoundException se Id não existir")
    void deveRetornarEntityNotFoundExceptionQuandoNaoExistirIdPacienteParaAtualizar() throws Exception {
        when(service.atualizar(
                any(DadosAtualizacaoPaciente.class),
                any(Long.class))
        ).thenThrow(EntityNotFoundException.class);

        mvc.perform(put("/pacientes/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dadosAtualizacaoPadrao()))
                ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deletar deve retornar 204 No Content")
    void deveRetornarNoContentQuandoPacienteForDeletado() throws Exception {

        mvc.perform(delete("/pacientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deletar deve retornar 404 EntityNotFoundException quando Id não existir")
    void deveRetornarEntityNotFoundExceptionQuandoNaoForDeletado() throws Exception {

        doThrow(new EntityNotFoundException())
                .when(service).deletar(99L);

        mvc.perform(delete("/pacientes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Listar Deletados deve retornar 200 Ok")
    void deveRetornarOkQuandoListarDeletados() throws Exception {
        when(service.listarDeletados(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mvc.perform(get("/pacientes/deletados"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.totalElements").exists());
    }

    private DadosCadastroPaciente dadosCadastroPadrao() {
        return new DadosCadastroPaciente(
                "Edson Castro",
                "edson.castro@email.com",
                "17993142873",
                "63202310090",
                dadosEnderecoPadrao());
    }

    private DadosEndereco dadosEnderecoPadrao() {
        return new DadosEndereco(
                "Avenida São Paulo",
                "1057",
                "Casa",
                "Centro",
                "São Paulo",
                "SP",
                "71467010"
        );
    }

    private Paciente pacientePadrao() {
        var paciente = new Paciente(
                "Edson Castro",
                "edson.castro@email.com",
                "17993142873",
                "63202310090",
                new Endereco(dadosEnderecoPadrao())
        );

        ReflectionTestUtils.setField(paciente, "id", 1L);

        return paciente;
    }

    private DadosAtualizacaoPaciente dadosAtualizacaoPadrao() {
        return new DadosAtualizacaoPaciente(
                "Novo Nome",
                "17999999999",
                dadosEnderecoPadrao()
        );
    }

    private Paciente pacienteAtualizado() {
        Paciente paciente = new Paciente(
                "Novo Nome",
                "edson.castro@email.com",
                "17999999999",
                "63202310090",
                new Endereco(dadosEnderecoPadrao())
        );

        ReflectionTestUtils.setField(paciente, "id", 1L);

        return paciente;
    }
}