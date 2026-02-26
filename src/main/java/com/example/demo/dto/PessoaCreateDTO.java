package com.example.demo.dto;

import com.example.demo.modelo.StatusAtendimento;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaCreateDTO {

    @NotBlank(message = "O nome precisa ser informado.")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "O nome deve conter apenas letras.")
    @Size(min = 3, message = "O nome deve possuir pelo menos três caracteres.")
    private String nome;

    @NotNull(message = "O serviço precisa ser informado.")
    private Integer servicoId;

    @NotNull(message = "A idade não pode ser vazia.")
    @Min(value = 1, message = "A idade precisa ser maior que 0.")
    @Max(value = 120, message = "A sua idade não pode ser maior que 120.")
    @Digits(integer = 3, fraction = 0, message = "A idade deve ser um número inteiro.")
    private Integer idade;

    @NotNull(message = "A data precisa ser informada.")
    private LocalDate dataAtendimento;

    private StatusAtendimento status;
}
