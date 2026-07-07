package com.alura.vollmed.infra.exception;

import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(ValidacaoConsultaException.class)
    public ResponseEntity<String> tratarValidacaoConsulta(ValidacaoConsultaException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarEntidadeNaoEncontrada() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarBeanValidation(
            MethodArgumentNotValidException ex) {

        var erros = ex.getFieldErrors()
                .stream()
                .map(DadosErroValidacao::new)
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> tratarJsonInvalido(
            HttpMessageNotReadableException ex) {

        return ResponseEntity
                .badRequest()
                .body("JSON inválido no corpo da requisição.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarErroGenerico(Exception ex) {
        ex.printStackTrace();

        return ResponseEntity
                .internalServerError()
                .body("Ocorreu um erro interno inesperado.");
    }

    private record DadosErroValidacao(String campo, String descricao) {

        public DadosErroValidacao(FieldError erro) {
            this(
                    erro.getField(),
                    erro.getDefaultMessage()
            );
        }
    }
}