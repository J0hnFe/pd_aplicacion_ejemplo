package org.programacion.distribuida;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.programacion.distribuida.servicios.StringService;

public class PrincipalCdi {


    public static void main(String[] args) {

        try(SeContainer container = SeContainerInitializer.newInstance().initialize()){
            StringService service = container.select(StringService.class).get();

            String ret = service.convert("hola");
            System.out.println(ret);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}