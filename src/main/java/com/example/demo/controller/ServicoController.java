package com.example.demo.controller;

import com.example.demo.dto.ServicoProteseDTO;
import com.example.demo.service.ServicoProteseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {


    private final ServicoProteseService servicoProteseService;

    @PostMapping
    public ResponseEntity<?>cadastrar(@Valid @RequestBody ServicoProteseDTO dto){
        return servicoProteseService.cadastrar(dto);
    }

    @GetMapping
    public ResponseEntity<?>lista(){
        return servicoProteseService.listar();
    }

}
