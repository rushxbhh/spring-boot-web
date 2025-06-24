package com.springbootweb.spring.boot.web.configs;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbootweb.spring.boot.web.entities.DeptEntity;
import com.springbootweb.spring.boot.web.dto.DeptDTO;

@Configuration
public class Mapper {

    @Bean
    public ModelMapper getMapper() {

return new ModelMapper();
    }
}



