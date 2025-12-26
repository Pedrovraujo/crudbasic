package com.example.demo.modelo;

import org.springframework.stereotype.Component;

@Component
public class Mensagem {
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    private String mensagem;
}
