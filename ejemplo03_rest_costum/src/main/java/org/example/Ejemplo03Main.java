package org.example;

import jakarta.ws.rs.SeBootstrap;

import java.net.URI;

public class Ejemplo03Main{
    public static void main(String[] args) throws Exception{
        System.out.println("Hello, World!");
        var config = SeBootstrap.Configuration.builder()
                .port(8080)
                .build();

        SeBootstrap.start(RestApplication.class, config)
                .thenApply(instance -> {
                    URI uri = instance.configuration().baseUri();

                    System.out.printf("Imstancia %s running at %s",
                            instance, uri);
                    return instance;
                }).exceptionally(ex->{
                    ex.printStackTrace();
                    return null;
                });

        Thread.currentThread().join();
    }
}