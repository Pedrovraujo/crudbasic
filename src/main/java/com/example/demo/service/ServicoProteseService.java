package com.example.demo.service;

import com.example.demo.dto.ServicoProteseDTO;
import com.example.demo.modelo.ServicoProtese;
import com.example.demo.repository.ServicoProteseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoProteseService {

    private final ServicoProteseRepository acao;
    private final ModelMapper mapper;

    public ResponseEntity<ServicoProteseDTO> cadastrar(ServicoProteseDTO dto) {
        ServicoProtese servico = mapper.map(dto, ServicoProtese.class);
        ServicoProtese salvo = acao.save(servico);
        return new ResponseEntity<>(mapper.map(salvo, ServicoProteseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<List<ServicoProteseDTO>> listar() {
        List<ServicoProteseDTO> lista = acao.findAll().stream()
                .map(s -> mapper.map(s, ServicoProteseDTO.class))
                .toList();
        return ResponseEntity.ok(lista);
    }
}
