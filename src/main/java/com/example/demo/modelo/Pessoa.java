package com.example.demo.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pessoas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome precisa ser informado.")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "O nome deve conter apenas letras.")
    @Size(min = 3, message = "O nome deve possuir pelo menos três caracteres.")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private ServicoProtese servico;

    @NotNull(message = "A idade não pode ser vazia.")
    @Min(value = 1, message = "A idade precisa ser maior que 0.")
    @Max(value = 120, message = "A sua idade não pode ser maior que 120.")
    @Digits(integer = 3, fraction = 0, message = "A idade deve ser um número inteiro.")
    private Integer idade;

    @NotNull(message = "A data precisa ser informada.")
    @Column(name = "dataAtendimento")
    private LocalDate dataAtendimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAtendimento status = StatusAtendimento.PENDENTE;

}



