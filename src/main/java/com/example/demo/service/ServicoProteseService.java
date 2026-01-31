package com.example.demo.service;

import com.example.demo.dto.ServicoProteseDTO;
import com.example.demo.modelo.Mensagem;
import com.example.demo.modelo.ServicoProtese;
import com.example.demo.repository.ServicoProteseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicoProteseService {

    private final Mensagem mensagem;
    private final ServicoProteseRepository acao;

    public ResponseEntity<?> cadastrar(ServicoProteseDTO dto) {
        ServicoProtese servico = new ServicoProtese();
        servico.setNomeTrabalho(dto.getNome_trabalho());
        servico.setDescricao(dto.getDescricao());
        return new ResponseEntity<>(acao.save(servico), HttpStatus.CREATED);
    }

    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
    }

}
