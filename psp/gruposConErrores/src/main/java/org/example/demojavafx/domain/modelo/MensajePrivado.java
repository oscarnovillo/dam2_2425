package org.example.demojavafx.domain.modelo;

import java.util.List;

public class MensajePrivado extends Mensaje{

    private List<MensajePrivadoUserClave> claves;

    public MensajePrivado(String texto, Grupo grupo) {
        super(texto, grupo);
    }
}
