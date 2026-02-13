package com.example.demo.controller;


import com.example.demo.dto.PessoaDTO;
import com.example.demo.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {


    private final PessoaService servico;


    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody PessoaDTO dto, JwtAuthenticationToken token) {
        return servico.cadastrar(dto, token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionarPeloCodigo(@PathVariable int id, JwtAuthenticationToken token) {
        return servico.selecionarPeloCodigo(id, token);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable int id,@Valid @RequestBody PessoaDTO dto, JwtAuthenticationToken token) {;
        return servico.editar(id,dto, token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable int id, JwtAuthenticationToken token) {
        return servico.remover(id,token);
    }

    @GetMapping
    public ResponseEntity<?> selecionar(JwtAuthenticationToken token) {
        return servico.selecionar(token);
    }

    @GetMapping("/data")
    public ResponseEntity<?> listarPorData(@RequestParam LocalDate data, JwtAuthenticationToken token) {
        return servico.listarPorData(data, token);
    }

}
