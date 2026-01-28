package com.example.demo.service;


import com.example.demo.dto.PessoaDTO;
import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.modelo.Mensagem;
import com.example.demo.modelo.Pessoa;
import com.example.demo.modelo.ServicoProtese;
import com.example.demo.repository.PessoaRepositorio;
import com.example.demo.repository.ServicoProteseRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final Mensagem mensagem;
    private final PessoaRepositorio acao;
    private final ServicoProteseRepository servicoProteseRepository;
    private final ModelMapper mapper;


    public ResponseEntity<?> cadastrar (PessoaDTO dto) {
        Optional<ServicoProtese> servicoOpt = servicoProteseRepository.findById(dto.getServicoId());
        if (servicoOpt.isEmpty()) {
            mensagem.setMensagem("Serviço não encontrado.");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);

        }

        Pessoa pessoa = mapper.map(dto, Pessoa.class);
        pessoa.setId(null);
        pessoa.setServico(servicoOpt.get());

        Pessoa salva = acao.save(pessoa);

        PessoaResponseDTO resposta = mapper.map(salva, PessoaResponseDTO.class);
        resposta.setNomeServico(salva.getServico().getNomeTrabalho());
        return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }


    public ResponseEntity<?> selecionar() {
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> selecionarPeloCodigo(Integer id) {
        return (ResponseEntity<?>) acao.findById(id)
                .<ResponseEntity<?>>map(pessoa ->  new ResponseEntity<>(pessoa, HttpStatus.OK))
                .orElseGet(() -> {
                    mensagem.setMensagem("Não foi encontrado nenhuma pessoa com esse código.");
                    return new  ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
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


    public ResponseEntity<?> editar(Integer id, PessoaDTO dto) {
        Pessoa pessoa = acao.findById(id)
                .orElseThrow(() -> {
                    mensagem.setMensagem("Pessoa não encontrada.");
                    return new RuntimeException(mensagem.getMensagem());
                });
        ServicoProtese novoServico = servicoProteseRepository.findById(dto.getServicoId())
                .orElseThrow(() -> {
                    mensagem.setMensagem("Serviço não encontrado.");
                    return new RuntimeException(mensagem.getMensagem());
                });
        pessoa.setNome(dto.getNome());
        pessoa.setIdade(dto.getIdade());
        pessoa.setServico(novoServico);
        Pessoa salva = acao.save(pessoa);
        PessoaResponseDTO resposta = mapper.map(salva, PessoaResponseDTO.class);
        resposta.setNomeServico(novoServico.getNomeTrabalho());

        return ResponseEntity.ok(resposta);
    }

}
