package org.example.springjavafx.config;



import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Getter
@Log4j2
@Component
public class Configuracion {

    private String pathDatos;
    private int numeroSuspensos;
    public Configuracion() {



    }


}
