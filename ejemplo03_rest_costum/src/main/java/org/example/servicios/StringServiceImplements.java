package org.example.servicios;

import org.example.servicios.StringService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StringServiceImplements implements StringService {


    @Override
    public String convert(String txt) {
        txt.toString();
        return txt.toUpperCase();
    }
}
