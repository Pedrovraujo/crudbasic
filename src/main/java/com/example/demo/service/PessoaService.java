package com.example.demo.service;

import com.example.demo.dto.PessoaCreateDTO;
import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.dto.PessoaUpdateDTO;
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

    private Integer getUsuarioId(JwtAuthenticationToken token) {
        try {
            return Integer.valueOf(token.getName());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Token inválido: subject não é um ID numérico.");
        }
    }

    private boolean isAdmin(JwtAuthenticationToken token) {
        return token.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"));
    }

    private boolean temPermissao(Pessoa pessoa, JwtAuthenticationToken token) {
        return isAdmin(token) || pessoa.getUsuario().getUserId().equals(getUsuarioId(token));
    }


    private Pessoa fromCreateDTO(PessoaCreateDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(dto.getNome());
        pessoa.setIdade(dto.getIdade());
        pessoa.setDataAtendimento(dto.getDataAtendimento());
        pessoa.setStatus(dto.getStatus() != null ? dto.getStatus() : pessoa.getStatus());
        return pessoa;
    }


    private void applyUpdateDTO(PessoaUpdateDTO dto, Pessoa pessoa) {
        pessoa.setNome(dto.getNome());
        pessoa.setIdade(dto.getIdade());
        pessoa.setDataAtendimento(dto.getDataAtendimento());
        if (dto.getStatus() != null) {
            pessoa.setStatus(dto.getStatus());
        }
    }
    public ResponseEntity<PessoaResponseDTO> cadastrar(PessoaCreateDTO dto, JwtAuthenticationToken token) {
        var usuario = usuarioRepository.findById(getUsuarioId(token))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var servico = servicoProteseRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Pessoa pessoa = fromCreateDTO(dto);
        pessoa.setUsuario(usuario);
        pessoa.setServico(servico);

        return new ResponseEntity<>(mapper.map(acao.save(pessoa), PessoaResponseDTO.class), HttpStatus.CREATED);
    }

    public ResponseEntity<List<PessoaResponseDTO>> selecionar(JwtAuthenticationToken token) {
        List<Pessoa> pessoas = isAdmin(token)
                ? acao.findAll()
                : acao.findByUsuarioUserId(getUsuarioId(token));

        return ResponseEntity.ok(pessoas.stream()
                .map(p -> mapper.map(p, PessoaResponseDTO.class))
                .toList());
    }

    public ResponseEntity<?> selecionarPeloCodigo(Integer id, JwtAuthenticationToken token) {
        Pessoa pessoa = acao.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada."));

        if (!temPermissao(pessoa, token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Você não tem permissão para visualizar este registro."));
        }

        return ResponseEntity.ok(mapper.map(pessoa, PessoaResponseDTO.class));
    }

    public ResponseEntity<?> remover(Integer id, JwtAuthenticationToken token) {
        Pessoa pessoa = acao.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada."));

        if (!temPermissao(pessoa, token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Você não tem permissão para remover este registro."));
        }

        acao.delete(pessoa);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> editar(Integer id, PessoaUpdateDTO dto, JwtAuthenticationToken token) {
        Pessoa pessoaExistente = acao.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada."));

        if (!temPermissao(pessoaExistente, token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Você não tem permissão para editar este registro."));
        }

        applyUpdateDTO(dto, pessoaExistente);

        if (dto.getServicoId() != null) {
            var servico = servicoProteseRepository.findById(dto.getServicoId())
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
            pessoaExistente.setServico(servico);
        }

        return ResponseEntity.ok(mapper.map(acao.save(pessoaExistente), PessoaResponseDTO.class));
    }

    public ResponseEntity<List<PessoaResponseDTO>> listarPorData(LocalDate data, JwtAuthenticationToken token) {
        List<Pessoa> pessoas = isAdmin(token)
                ? acao.findByDataAtendimento(data)
                : acao.findByDataAtendimentoAndUsuarioUserId(data, getUsuarioId(token));

        return ResponseEntity.ok(pessoas.stream()
                .map(p -> mapper.map(p, PessoaResponseDTO.class))
                .toList());
    }
}
