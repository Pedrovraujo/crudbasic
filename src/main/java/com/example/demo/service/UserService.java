package com.example.demo.service;

import com.example.demo.dto.CreateUserDto;
import com.example.demo.modelo.Role;
import com.example.demo.modelo.Usuario;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleRepository roleRepository;

    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<?> newuser(CreateUserDto dto) {
        if (usuarioRepository.findByUsername(dto.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Este nome de usuário já está em uso."));
        }

        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
        if (basicRole == null) {
            basicRole = new Role();
            basicRole.setName(Role.Values.BASIC.name());
            basicRole = roleRepository.save(basicRole);
        }

        var user = new Usuario();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(new HashSet<>(Set.of(basicRole)));

        usuarioRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
