package com.example.demo.repository;


import com.example.demo.modelo.Pessoa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Repositorio extends CrudRepository<Pessoa, Integer> {


}
