package com.example.demo.service;

import com.example.demo.dto.CreateUserDto;
import com.example.demo.modelo.Role;
import com.example.demo.modelo.Usuario;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UsuarioRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
@RequiredArgsConstructor
@Data
public class UserService {

    private final RoleRepository roleRepository;

    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<?> newuser(CreateUserDto dto) {
        usuarioRepository.findByUsername(dto.username()).ifPresent(user -> {
            throw new RuntimeException("Este nome de usuário já está em uso.");
        });

        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        var user = new Usuario();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(basicRole));

        usuarioRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
