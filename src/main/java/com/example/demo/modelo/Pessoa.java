package com.example.demo.modelo;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private ServicoProtese servico;

    private Integer idade;


    private LocalDate dataAtendimento;

    @Enumerated(EnumType.STRING)
    private StatusAtendimento status = StatusAtendimento.PENDENTE;

}
