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

    public ResponseEntity<?> cadastrar(Pessoa obj){
        mensagem.setMensagem(("O nome precisa ser preenchido"));
        if (obj.getNome().equals("")) {
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else if (obj.getIdade() < 0) {
            mensagem.setMensagem("Idade tem que ser maior que 0");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else if (obj.getTrabalho().equals("")) {
            mensagem.setMensagem("Informe o trabalho a ser realizado.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(acao.save(obj), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> selecionar (){
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> selecionarPeloCodigo(int id){
        if (acao.countById(id) == 0){
            mensagem.setMensagem("Não foi encontrado nenhuma pessoa com esse codigo.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(acao.findById(id), HttpStatus.OK);
        }
    }



    public ResponseEntity<?> remover(int id){
        if (acao.countById(id) == 0){
            mensagem.setMensagem("O codigo informado nao existe.");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }else{
            Pessoa obj = acao.findById(id);
            acao.delete(obj);
            mensagem.setMensagem("Pessoa removida com sucesso!");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> editar(Pessoa obj){if (acao.countById(obj.getId())== 0 ){
        mensagem.setMensagem("O codigo informado nao existe.");
        return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
    } else if (obj.getNome().equals("")) {
        mensagem.setMensagem("É necesario informar um nome.");
        return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
    } else if (obj.getTrabalho().equals("")) {
        mensagem.setMensagem("E necesarrio informar o trabalho a ser realizado.");
        return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
    }else if (obj.getIdade() < 0) {
        mensagem.setMensagem("Informe uma idade válida.");
        return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
    } else {
        return new ResponseEntity<>(acao.save(obj), HttpStatus.OK);
    }
    }


}
