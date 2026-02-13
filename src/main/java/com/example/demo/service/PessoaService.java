package com.example.demo.service;

import com.example.demo.dto.PessoaDTO;
import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.modelo.Pessoa;
import com.example.demo.repository.PessoaRepositorio;
import com.example.demo.repository.ServicoProteseRepository;
import com.example.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepositorio acao;
    private final ServicoProteseRepository servicoProteseRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper mapper;

    public ResponseEntity<?> cadastrar(PessoaDTO dto, JwtAuthenticationToken token) {
        var usuario = usuarioRepository.findById(Integer.valueOf(token.getName()))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var servico = servicoProteseRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        
        Pessoa pessoa = mapper.map(dto, Pessoa.class);

        pessoa.setId(null);

        pessoa.setUsuario(usuario);
        pessoa.setServico(servico);

        Pessoa pessoaSalva = acao.save(pessoa);
        return new ResponseEntity<>(mapper.map(pessoaSalva, PessoaResponseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<?> selecionar(JwtAuthenticationToken token) {
        Integer userId = Integer.valueOf(token.getName());
        boolean isAdmin = token.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"));

        List<Pessoa> pessoas = isAdmin ? acao.findAll() : acao.findByUsuarioUserId(userId);

        return ResponseEntity.ok(pessoas.stream()
                .map(p -> mapper.map(p, PessoaResponseDTO.class))
                .toList());
    }

    public ResponseEntity<?> selecionarPeloCodigo(Integer id, JwtAuthenticationToken token) {
        Pessoa pessoa = acao.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada."));

        Integer usuarioLogadoId = Integer.valueOf(token.getName());
        boolean isAdmin = token.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"));

        if (!isAdmin && !pessoa.getUsuario().getUserId().equals(usuarioLogadoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Você não tem permissão para visualizar este registro."));
        }

        return ResponseEntity.ok(mapper.map(pessoa, PessoaResponseDTO.class));
    }

    public ResponseEntity<?> remover(Integer id, JwtAuthenticationToken token) {
        Pessoa pessoa = acao.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada."));

        Integer usuarioLogadoId = Integer.valueOf(token.getName());
        boolean isAdmin = token.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"));

        if (!isAdmin && !pessoa.getUsuario().getUserId().equals(usuarioLogadoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        acao.delete(pessoa);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> editar(Integer id, PessoaDTO dto, JwtAuthenticationToken token) {
        Pessoa pessoaExistente = acao.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada."));

        Integer usuarioLogadoId = Integer.valueOf(token.getName());
        boolean isAdmin = token.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"));

        if (!isAdmin && !pessoaExistente.getUsuario().getUserId().equals(usuarioLogadoId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Você não tem permissão para editar este registro."));
        }

        mapper.map(dto, pessoaExistente);

        if (dto.getServicoId() != null) {
            var servico = servicoProteseRepository.findById(dto.getServicoId())
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
            pessoaExistente.setServico(servico);
        }

        return ResponseEntity.ok(mapper.map(acao.save(pessoaExistente), PessoaResponseDTO.class));
    }

    public ResponseEntity<?> listarPorData(LocalDate data, JwtAuthenticationToken token) {
        Integer userId = Integer.valueOf(token.getName());

        List<Pessoa> pessoas = acao.findByDataAtendimentoAndUsuarioUserId(data, userId);

        return ResponseEntity.ok(pessoas.stream()
                .map(p -> mapper.map(p, PessoaResponseDTO.class))
                .toList());
    }
}
