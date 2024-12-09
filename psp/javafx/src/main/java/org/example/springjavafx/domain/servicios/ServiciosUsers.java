package org.example.springjavafx.domain.servicios;

import io.vavr.control.Either;
import org.example.springjavafx.domain.modelo.Usuario;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ServiciosUsers {


    public String getNombreUsuario() {
        return "Usuario";
    }

    @Async
    public CompletableFuture<Either<String,Boolean>> login(Usuario usuario)  {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("lkj");
        }
        if (usuario.getName().equals("admin"))
            return CompletableFuture.completedFuture(Either.right(true));
        else
            return CompletableFuture.completedFuture(Either.left("Usuario no encontrado"));
    }

    public String cargarUsuarios() { return "";}
}
