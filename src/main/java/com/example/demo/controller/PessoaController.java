package com.example.demo.controller;


import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.Repositorio;
import com.example.demo.service.Servico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {


    @Autowired
    private Repositorio acao;

    @Autowired
    private Servico servico;


    @PostMapping("/")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Pessoa obj){
        return new ResponseEntity<>(acao.save(obj), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> selecionarPeloCodigo(@PathVariable int id){
        return servico.selecionarPeloCodigo(id);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editar(@Valid @RequestBody Pessoa obj){
        return servico.editar(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable int id){
        return servico.remover(id);
    }

    @GetMapping("")
    public ResponseEntity<?> selecionar(){return servico.selecionar();
    }

    @GetMapping("/status")
    public ResponseEntity<?> status(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
