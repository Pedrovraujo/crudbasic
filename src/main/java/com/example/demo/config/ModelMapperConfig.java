package com.example.demo.config;

import com.example.demo.dto.PessoaDTO;
import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.modelo.Pessoa;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Pessoa.class, PessoaResponseDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getServico().getNomeTrabalho(), PessoaResponseDTO::setNomeServico);
            mapper.map(src -> src.getServico().getDescricao(), PessoaResponseDTO::setDescricaoServico);
        });

        modelMapper.typeMap(PessoaDTO.class, Pessoa.class).addMappings(mapper -> {
            mapper.skip(Pessoa::setId);
        });

        return modelMapper;
    }
}