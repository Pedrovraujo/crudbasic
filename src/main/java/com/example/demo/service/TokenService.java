package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.modelo.Role;
import com.example.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<LoginResponseDTO> login(LoginDTO loginDTO) {
        var user = usuarioRepository.findByUsername(loginDTO.username())
                .filter(u -> u.isLoginCorrect(loginDTO, passwordEncoder))
                .orElseThrow(() -> new BadCredentialsException("Usuário ou senha inválidos"));

        var now = Instant.now();
        var expiresIn = 43200L;

        String scopes = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("backend")
                .subject(user.getUserId().toString())
                .claim("scope", scopes)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(tokenValue, expiresIn));
    }
}
