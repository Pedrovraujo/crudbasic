package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

        @NotBlank(message = "O username precisa ser informado.")
        String username,

        @NotBlank(message = "A senha precisa ser informada.")
        String password

) {}
