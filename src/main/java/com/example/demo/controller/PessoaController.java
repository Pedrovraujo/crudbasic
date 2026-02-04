package com.example.demo.controller;


import com.example.demo.dto.PessoaDTO;
import com.example.demo.modelo.Pessoa;
import com.example.demo.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {


    private final PessoaService servico;


    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody PessoaDTO dto) {
        return servico.cadastrar(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionarPeloCodigo(@PathVariable int id) {
        return servico.selecionarPeloCodigo(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable int id,@Valid @RequestBody PessoaDTO dto) {;
        return servico.editar(id,dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable int id) {
        return servico.remover(id);
    }

    @GetMapping
    public ResponseEntity<?> selecionar() {
        return servico.selecionar();
    }

    @GetMapping("/data")
    public ResponseEntity<?> listarPorData(@RequestParam String data) {
        LocalDate dia = LocalDate.parse(data);
        return servico.listarPorData(dia);
    }

}
