package com.example.demo.modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "servicos_protese")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicoProtese {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeTrabalho;
    private String descricao;

}
