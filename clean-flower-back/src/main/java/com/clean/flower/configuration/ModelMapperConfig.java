package com.clean.flower.configuration;

import com.clean.flower.util.ConvertidorFecha;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * Clase de configuracion de model mapper.
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        Converter<Date,String> convertidorFecha = new ConvertidorFecha();
        modelMapper.addConverter(convertidorFecha);
        return modelMapper;
    }
}
