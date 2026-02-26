package com.example.demo.controller;

import com.example.demo.dto.PessoaCreateDTO;
import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.dto.PessoaUpdateDTO;
import com.example.demo.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService servico;

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> cadastrar(
            @Valid @RequestBody PessoaCreateDTO dto,
            JwtAuthenticationToken token) {
        return servico.cadastrar(dto, token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionarPeloCodigo(
            @PathVariable int id,
            JwtAuthenticationToken token) {
        return servico.selecionarPeloCodigo(id, token);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(
            @PathVariable int id,
            @Valid @RequestBody PessoaUpdateDTO dto,
            JwtAuthenticationToken token) {
        return servico.editar(id, dto, token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(
            @PathVariable int id,
            JwtAuthenticationToken token) {
        return servico.remover(id, token);
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> selecionar(JwtAuthenticationToken token) {
        return servico.selecionar(token);
    }

    @GetMapping("/data")
    public ResponseEntity<List<PessoaResponseDTO>> listarPorData(
            @RequestParam LocalDate data,
            JwtAuthenticationToken token) {
        return servico.listarPorData(data, token);
    }
}
