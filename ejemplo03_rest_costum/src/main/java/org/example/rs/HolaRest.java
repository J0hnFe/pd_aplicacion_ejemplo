package org.example.rs;

import org.example.servicios.StringService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/hello")
public class HolaRest {
    //http://127.0.0.1:8080/contexto/hello?txt=holamundo

    @Inject
    StringService servicios;

    @GET
    public String hello(@QueryParam("txt") String txt){
        return servicios.convert(txt);
    }
}
