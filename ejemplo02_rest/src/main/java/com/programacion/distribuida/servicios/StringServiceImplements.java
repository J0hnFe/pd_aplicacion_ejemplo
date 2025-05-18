package com.programacion.distribuida.servicios;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.CDI;
import com.programacion.distribuida.servicios.StringService;

@ApplicationScoped
public class StringServiceImplements implements StringService {


    @Override
    public String convert(String txt) {
        txt.toString();
        return txt.toUpperCase();
    }
}
