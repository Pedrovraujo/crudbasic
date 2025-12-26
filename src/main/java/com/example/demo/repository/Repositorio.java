package com.example.demo.repository;


import com.example.demo.modelo.Pessoa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Repositorio extends CrudRepository<Pessoa, Integer> {


    List<Pessoa> findAll();

    int countById(int id);

    Pessoa findById(int id);


}
