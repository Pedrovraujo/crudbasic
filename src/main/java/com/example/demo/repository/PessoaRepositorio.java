package com.example.demo.repository;


import com.example.demo.modelo.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface PessoaRepositorio extends JpaRepository<Pessoa, Integer> {
    List<Pessoa> findByDataAtendimento(LocalDate data);

    List<Pessoa> findByDataAtendimentoAndUsuarioUserId(LocalDate data, Integer userId);

    List<Pessoa> findByUsuarioUserId(Integer userId);

}
