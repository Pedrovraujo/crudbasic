package com.example.demo.repository;


import com.example.demo.modelo.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PessoaRepositorio extends JpaRepository<Pessoa, Integer> {


}
