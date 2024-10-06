package org.example.demojavafx.data;

import org.example.demojavafx.domain.modelo.Mensaje;

import java.util.List;

public class DaoMensajes {

    private final DataBase dataBase;

    public DaoMensajes() {
        this.dataBase = new DataBase();
    }


    public boolean registerMensaje(Mensaje mensajeNuevo) {

        List<Mensaje> mensajes = dataBase.loadMensajes();
        mensajes.add(mensajeNuevo);
        return dataBase.saveMensajes(mensajes);

    }

    public List<Mensaje> getMensajesOfGrupo(String grupoName) {
        return dataBase.loadMensajes().stream().filter(mensaje -> mensaje.getGrupo().getNombre().equals(grupoName))
                .toList();
    }
}
