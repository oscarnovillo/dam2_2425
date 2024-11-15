package org.example.demojavafx.domain.servicios;

import io.vavr.control.Either;
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

    private Either<ErrorApp,List<Message>> desencriptarMensajes(List<Message> mensajes, String pwd) {

        // Usamos un Stream para procesar los mensajes
        List<Either<ErrorApp, Message>> result = mensajes.stream()
                .map(message -> decryptMessage(message, pwd)) // Desencriptamos cada mensaje
                .toList(); // Recopilamos los resultados en una lista

        // Verificamos si hay errores en la lista de resultados
        List<ErrorApp> errors = result.stream()
                .filter(Either::isLeft) // Filtramos los mensajes con error
                .map(Either::getLeft) // Extraemos los errores
                .toList();

        if (!errors.isEmpty()) {
            // Si encontramos algún error, devolvemos el primer error
            return Either.left(errors.getFirst());
        }

        // Si no hubo errores, devolvemos los mensajes desencriptados
        List<Message> validMessages = result.stream()
                .filter(Either::isRight) // Filtramos los mensajes exitosos
                .map(Either::get) // Extraemos los mensajes
                .toList();

        return Either.right(validMessages); // Devolvemos la lista de mensajes exitosos

    }


    public List<Mensaje> getMensajesOfGrupo(String grupoName) {
        return daoMensajes.getMensajesOfGrupo(grupoName);
    }
}
