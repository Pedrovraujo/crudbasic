package com.example.demo.service;


import com.example.demo.modelo.Mensagem;
import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Servico {


    @Autowired
    private Mensagem mensagem;

    @Autowired
    private Repositorio acao;


    public ResponseEntity<?> selecionar (){
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> selecionarPeloCodigo(Integer id) {
        return acao.findById(id)
                .<ResponseEntity<?>>map(pessoa -> new ResponseEntity<>(pessoa, HttpStatus.OK))
                .orElseGet(() -> {
                    mensagem.setMensagem("Não foi encontrado nenhuma pessoa com esse código.");
                    return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
                });
    }


    public ResponseEntity<?> remover(int id) {
        if (!acao.existsById(id)) {
            mensagem.setMensagem("O código informado não existe.");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
        acao.deleteById(id);
        mensagem.setMensagem("Pessoa removida com sucesso!");
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }


    public ResponseEntity<?> editar(Pessoa obj) {
        if (!acao.existsById(obj.getId())) {
            mensagem.setMensagem("O código informado não existe.");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(acao.save(obj), HttpStatus.OK);
        }
    }

}
