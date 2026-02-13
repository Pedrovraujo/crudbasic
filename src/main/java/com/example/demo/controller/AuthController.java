package com.example.demo.controller;


import com.example.demo.dto.LoginDTO;
import com.example.demo.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;


    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return tokenService.login(loginDTO);

    }

}
