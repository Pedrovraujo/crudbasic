package com.example.demo.dto;

import com.example.demo.modelo.StatusAtendimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponseDTO {
    private Integer id;
    private String nome;
    private Integer idade;
    private String nomeServico;
    private String descricaoServico;
    private StatusAtendimento status;
}
