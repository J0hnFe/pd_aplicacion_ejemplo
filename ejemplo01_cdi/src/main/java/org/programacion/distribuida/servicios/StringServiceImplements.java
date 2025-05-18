package org.programacion.distribuida.servicios;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;

@ApplicationScoped
public class StringServiceImplements implements StringService{

    //@Inject
    LogService log;

    //para inicializar sin el Inject
    @PostConstruct
    void init(){
        log = CDI.current()
                .select(LogService.class)
                .get();
    }

    @Override
    public String convert(String txt) {
        log.print("convert: " + txt);
        return txt.toUpperCase();
    }
}
