package com.alura.vollmed.controller;

import com.alura.vollmed.domain.usuario.DadosAutenticacaoUsuario;
import com.alura.vollmed.domain.usuario.Usuario;
import com.alura.vollmed.infra.security.DadosTokenJWT;
import com.alura.vollmed.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity loginUsuario(@RequestBody @Valid DadosAutenticacaoUsuario dados){
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var autenthication = manager.authenticate(token);

        var tokenJWT = tokenService.gerarToken((Usuario) autenthication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
