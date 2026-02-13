package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoProteseDTO {

    @NotBlank(message = "O trabalho precisa ser informado")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "O nome deve conter apenas letras.")
    private String nomeTrabalho;

    private String descricao;
}
