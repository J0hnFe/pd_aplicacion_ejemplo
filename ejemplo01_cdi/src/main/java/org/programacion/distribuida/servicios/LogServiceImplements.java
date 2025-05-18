package org.programacion.distribuida.servicios;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LogServiceImplements implements LogService{

    @Override
    public void print(String txt) {
        System.out.println("[LOG] "+txt);
    }
}
