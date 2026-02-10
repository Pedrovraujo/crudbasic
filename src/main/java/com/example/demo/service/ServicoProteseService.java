package com.example.demo.service;

import com.example.demo.dto.ServicoProteseDTO;
import com.example.demo.modelo.ServicoProtese;
import com.example.demo.repository.ServicoProteseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ServicoProteseService {

    private final ServicoProteseRepository acao;
    private final ModelMapper mapper;

    public ResponseEntity<?> cadastrar(ServicoProteseDTO dto) {
        ServicoProtese servico = mapper.map(dto, ServicoProtese.class);
        return new ResponseEntity<>(acao.save(servico), HttpStatus.CREATED);
    }

    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(acao.findAll().stream()
                .map(s -> mapper.map(s, ServicoProteseDTO.class))
                .toList());
    }
}
