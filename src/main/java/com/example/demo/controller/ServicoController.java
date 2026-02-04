package com.example.demo.controller;

import com.example.demo.dto.ServicoProteseDTO;
import com.example.demo.modelo.Mensagem;
import com.example.demo.modelo.ServicoProtese;
import com.example.demo.repository.ServicoProteseRepository;
import com.example.demo.service.ServicoProteseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {


    private final ServicoProteseService servicoProteseService;

    private final ServicoProteseRepository acao;

    @PostMapping
    public ResponseEntity<?>cadastrar(@Valid @RequestBody ServicoProteseDTO dto){
        return servicoProteseService.cadastrar(dto);
    }

    @GetMapping
    public ResponseEntity<?>lista(){
        return servicoProteseService.listar();
    }

}
