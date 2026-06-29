package com.alura.vollmed.infra.exception;

import com.alura.vollmed.domain.consulta.ValidacaoConsultaException;
import com.alura.vollmed.domain.consulta.validador.ValidadorAgendamento;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(ValidacaoConsultaException.class)
    public ResponseEntity<String> tratarErrosValidacaoAgendamento(ValidacaoConsultaException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarErroGenerico(Exception ex) {
        ex.printStackTrace();

        return ResponseEntity.badRequest().body(ex.getClass().getName());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratrarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(
            MethodArgumentNotValidException ex
    ) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(
                erros.stream().map(DadosErroValidacao::new).toList()
        );
    }

    private record DadosErroValidacao(String campo, String descricao) {
        public DadosErroValidacao(FieldError erro){
            this(
                    erro.getField(), erro.getDefaultMessage()
            );
        }
    }
}
