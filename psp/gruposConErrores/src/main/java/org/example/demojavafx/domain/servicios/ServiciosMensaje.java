package org.example.demojavafx.domain.servicios;

import org.example.demojavafx.data.DaoMensajes;
import org.example.demojavafx.domain.modelo.Mensaje;

import java.util.List;

public class ServiciosMensaje {

    private final DaoMensajes daoMensajes;

    public ServiciosMensaje() {
        daoMensajes = new DaoMensajes();
    }

    public boolean addMensajeToGrupo(Mensaje mensajeNuevo) {
        return daoMensajes.registerMensaje(mensajeNuevo);
    }

    public List<Mensaje> getMensajesOfGrupo(String grupoName) {
        return daoMensajes.getMensajesOfGrupo(grupoName);
    }
}
