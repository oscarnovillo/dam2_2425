package org.example.demojavafx.data;

import io.vavr.control.Either;
import org.example.demojavafx.domain.errors.ErrorApp;
import org.example.demojavafx.domain.errors.ErrorAppDatosNoValidos;
import org.example.demojavafx.domain.modelo.User;

public class DaoUsers {

    private final DataBase dataBase;

    public DaoUsers() {
        this.dataBase = new DataBase();
    }


    public Either<ErrorApp,User> getUserbyName(String username) {
        return dataBase.loadUsuarios()
                .flatMap(usuarios -> usuarios.stream()
                        .filter(user -> user.getUsername().equals(username))
                        .findFirst()
                        .map(Either::<ErrorApp, User>right)
                        .orElseGet(() -> Either.left(new ErrorAppDatosNoValidos("User not found: " + username)))
                );
    }

    public Either<ErrorApp, Boolean> registerUser(User usuarioNuevo) {

        return dataBase.loadUsuarios().map(usuarios ->
        {
            usuarios.add(usuarioNuevo);
            return dataBase.saveUsuarios(usuarios);
        });

    }

}
