package com.programacion.distribuida.authors;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.consul.ConsulClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.InetAddress;
import java.util.UUID;

@ApplicationScoped // Con esto hago q sea un componente CDI
public class AuthorsLifeCycle {

    @Inject
    @ConfigProperty(name = "consul.host", defaultValue = "8500")
    String consulHost;

    @Inject
    @ConfigProperty(name = "consul.port", defaultValue = "127.0.0.1")
    Integer consulPort;

    @Inject
    @ConfigProperty(name = "quarkus.http.port")
    Integer appPort;

    String serviceId;

    // Necesitamos escuchar 2 eventos: Inicializacion y cuando se detiene
    // Cuando arranca
    void init(@Observes StartupEvent event, Vertx vertx) throws Exception {
        System.out.println("Iniciando servicio de autoresss...");

        // Obtenemos host y puerto de la configuracion
        ConsulClientOptions options = new ConsulClientOptions()
                .setHost(consulHost)
                .setPort(consulPort);
        ConsulClient consulClient = ConsulClient.create(vertx, options);

        serviceId = UUID.randomUUID().toString();
        var ipAddress = InetAddress.getLocalHost();


        ServiceOptions serviceOptions = new ServiceOptions()
                .setName("app-authors")
                .setId(serviceId)
                .setAddress(ipAddress.getHostAddress())
                .setPort(appPort);
        consulClient.registerServiceAndAwait(serviceOptions);
    }

    // Cuando se detiene
    void stop(@Observes ShutdownEvent event, Vertx vertx) throws Exception {

        System.out.println("Deteniendo servicio de autoresss...");

        ConsulClientOptions options = new ConsulClientOptions()
                .setHost(consulHost)
                .setPort(consulPort);
        ConsulClient consulClient = ConsulClient.create(vertx, options);

        consulClient.deregisterServiceAndAwait(serviceId);

    }
}