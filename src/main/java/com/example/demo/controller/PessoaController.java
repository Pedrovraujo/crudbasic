package com.example.demo.controller;


import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.Repositorio;
import com.example.demo.service.Servico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {


    @Autowired
    private Repositorio acao;

    @Autowired
    private Servico servico;


    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Pessoa obj){
        return servico.cadastrar(obj);
    }

    @GetMapping("/selecionar/{id}")
    public ResponseEntity<?> selecionarPeloCodigo(@PathVariable int id){
        return servico.selecionarPeloCodigo(id);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editar(@RequestBody Pessoa obj){
        return servico.editar(obj);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> remover(@PathVariable int id){
        return servico.remover(id);
    }

    @GetMapping("/selecionar")
    public ResponseEntity<?> selecionar(){return servico.selecionar();
    }

    @GetMapping("/status")
    public ResponseEntity<?> status(){
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
