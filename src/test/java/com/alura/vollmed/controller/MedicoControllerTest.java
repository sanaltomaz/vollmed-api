package com.alura.vollmed.controller;

import com.alura.vollmed.domain.endereco.DadosEndereco;
import com.alura.vollmed.domain.medico.*;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(MedicoController.class)
@AutoConfigureMockMvc(addFilters = false)
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MedicoRepository repository;

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

        when(repository.save(any(Medico.class)))
                .thenAnswer(invocation -> {
                    Medico medico = invocation.getArgument(0);

                    ReflectionTestUtils.setField(
                            medico,
                            "id",
                            1L);

                    return medico;
                });

        mvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dados)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Cadastrar deve retornar 400 Bad Request quando Dados Inválidos")
    void deveRetornarBadRequestQuandoDadosInvalidos() throws Exception {

        var dadosInvalidos = new DadosCadastroMedico(
                "medicos",
                "medicos@gmail.com",
                "17999999999",
                "501",
                Especialidade.CARDIOLOGIA,
                dadosEnderecoPadrao()
        );

        mvc.perform(post("/medicos")
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

        mvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Listar deve retornar 200 Ok quando disparado")
    void deveRetornarOkQuandoListar() throws Exception {

        when(repository.findAllByAtivoTrue(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mvc.perform(get("/medicos"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Listar deve retornar JSON paginado")
    void deveRetornarJsonPaginado() throws Exception{
        when(repository.findAllByAtivoTrue(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mvc.perform(get("/medicos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.totalElements").exists())
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.size").exists());
    }

    @Test
    @DisplayName("Detalhar deve retornar 200 Ok se existir Id")
    void deveRetornarOkQuandoExistirIdMedico() throws Exception {
        when(repository.getReferenceById(1L))
                .thenReturn(medicoPadrao());

        mvc.perform(get("/medicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Edson Castro"))
                .andExpect(jsonPath("$.email").value("edson.castro@email.com"));
    }

    @Test
    @DisplayName("Detalhar deve retornar 404 EntityNotFoundException se não existir Id")
    void deveRetornarEntityNotFoundExceptionQuandoNaoExistirIdMedicoParaDetalhar() throws Exception {
        when(repository.getReferenceById(99L))
                .thenThrow(jakarta.persistence.EntityNotFoundException.class);

        mvc.perform(get("/medicos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Atualizar deve retornar 200 Ok quando Dados e Id corretos")
    void deveRetornarOkQuandoAtualizarMedico() throws Exception {

        when(repository.getReferenceById(1L))
                .thenReturn(medicoPadrao());

        mvc.perform(put("/medicos/1")
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

        mvc.perform(put("/medicos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInvalido)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Atualizar deve retornar 404 EntityNotFoundException se Id não existir")
    void deveRetornarEntityNotFoundExceptionQuandoNaoExistirIdMedicoParaAtualizar() throws Exception {
        when(repository.getReferenceById(99L))
                .thenThrow(jakarta.persistence.EntityNotFoundException.class);

        mvc.perform(put("/medicos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dadosAtualizacaoPadrao()))
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deletar deve retornar 204 No Content")
    void deveRetornarNoContentQuandoMedicoForDeletado() throws Exception {
        when(repository.getReferenceById(1L))
                .thenReturn(medicoPadrao());

        mvc.perform(delete("/medicos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deletar deve retornar 404 EntityNotFoundException quando Id não existir")
    void deveRetornarEntityNotFoundExceptionQuandoNaoForDeletado() throws Exception {
        when(repository.getReferenceById(99L))
                .thenThrow(jakarta.persistence.EntityNotFoundException.class);

        mvc.perform(delete("/medicos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Listar Deletados deve retornar 200 Ok")
    void deveRetornarOkQuandoListarDeletados() throws Exception {
        when(repository.findAllByAtivoFalse(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));

        mvc.perform(get("/medicos/deletados"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.totalElements").exists());
    }

    private DadosCadastroMedico dadosCadastroPadrao() {
        return new DadosCadastroMedico(
                "Edson Castro",
                "edson.castro@email.com",
                "17993142873",
                "101202",
                Especialidade.CARDIOLOGIA,
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

    private Medico medicoPadrao() {
        var medico = new Medico(dadosCadastroPadrao());

        ReflectionTestUtils.setField(medico, "id", 1L);

        return medico;
    }

    private DadosAtualizacaoMedico dadosAtualizacaoPadrao() {
        return new DadosAtualizacaoMedico(
                "Novo Nome",
                "17999999999",
                dadosEnderecoPadrao()
        );
    }
}