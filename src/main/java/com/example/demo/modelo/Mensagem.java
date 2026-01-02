package com.example.demo.modelo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Data
public class Mensagem {

    private String mensagem;
}
