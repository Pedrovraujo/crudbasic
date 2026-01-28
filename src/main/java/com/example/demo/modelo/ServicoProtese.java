package com.example.demo.modelo;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.XMLEncoder;

@Entity
@Table(name = "servicos_protese")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoProtese {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O trabalho precisa ser informado")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "O nome deve conter apenas letras.")
    private String nomeTrabalho;

    private String descricao;

}
