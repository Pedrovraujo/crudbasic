package com.example.demo.config;

import com.example.demo.modelo.Role;
import com.example.demo.modelo.Usuario;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AdminUsuarioConfig implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());

        var userAdmin = usuarioRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                (user)-> {System.out.println("admin já existe");
                    },
                ()->{
                    var user =new Usuario();
                    user.setUsername("admin");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin));
                    usuarioRepository.save(user);
                }
        );
    }
}
