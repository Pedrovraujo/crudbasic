package com.example.demo.controller;

import com.example.demo.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping
    public ResponseEntity<?> login(@RequestParam String user, @RequestParam String pass) {
        if ("admin".equals(user) && "1234".equals(pass)) {
            String token = JwtUtil.generateToken(user);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
