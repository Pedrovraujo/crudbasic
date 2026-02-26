package com.example.demo.config;

import com.example.demo.dto.PessoaResponseDTO;
import com.example.demo.modelo.Pessoa;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Pessoa, String> toNomeServico =
                ctx -> ctx.getSource().getServico() != null
                        ? ctx.getSource().getServico().getNomeTrabalho()
                        : null;

        Converter<Pessoa, String> toDescricaoServico =
                ctx -> ctx.getSource().getServico() != null
                        ? ctx.getSource().getServico().getDescricao()
                        : null;

        modelMapper.typeMap(Pessoa.class, PessoaResponseDTO.class).addMappings(mapper -> {
            mapper.using(toNomeServico).map(src -> src, PessoaResponseDTO::setNomeServico);
            mapper.using(toDescricaoServico).map(src -> src, PessoaResponseDTO::setDescricaoServico);
        });

        return modelMapper;
    }
}
