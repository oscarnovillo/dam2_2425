package org.example.demojavafx.domain.servicios;

import org.example.demojavafx.data.DaoMensajes;
import org.example.demojavafx.domain.modelo.Mensaje;
import org.example.demojavafx.domain.modelo.User;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

public class ServiciosMensaje {

    private final DaoMensajes daoMensajes;

    public ServiciosMensaje() {
        daoMensajes = new DaoMensajes();
    }

    public boolean addMensajeToGrupo(Mensaje mensajeNuevo) {
        return daoMensajes.registerMensaje(mensajeNuevo);
    }
    public boolean addMensajeToGrupoPrivado(Mensaje mensajeNuevo, User emisor) {
        // generar Clave simetrica aletoria
        SecureRandom sr = new SecureRandom();
        byte[] claveSimetrica = new byte[16];
        sr.nextBytes(claveSimetrica);
        String clave = Base64.getUrlEncoder().encodeToString(claveSimetrica);

        // lalmar a encriptar simetricamente el mensaje

        // buscar miembros del grupo privado
        List<User> miembros = null;

        // encriptar la cave simetrica con la clave publica de cada miembro
        miembros.forEach(Asimetrico.encript(clave,dameClavePublic(emisor.getUsername())));


        return daoMensajes.registerMensaje(mensajeNuevo);
    }

    public List<Mensaje> getMensajesOfGrupoPrivado(String grupoName,User user) {
        return daoMensajes.getMensajesOfGrupo(grupoName);
        // cogere el campo claves del Mensaje correspondiente a mi usuario

        // desencriptare la clave simetrica con mi clave privada


    }


    public List<Mensaje> getMensajesOfGrupo(String grupoName) {
        return daoMensajes.getMensajesOfGrupo(grupoName);
    }
}
