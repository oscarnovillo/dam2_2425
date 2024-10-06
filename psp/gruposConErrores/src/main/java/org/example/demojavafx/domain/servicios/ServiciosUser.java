package org.example.demojavafx.domain.servicios;

import io.vavr.control.Either;
import org.example.demojavafx.data.DaoUsers;
import org.example.demojavafx.domain.errors.ErrorAppDatosNoValidos;
import org.example.demojavafx.domain.modelo.User;
import org.example.demojavafx.domain.errors.ErrorApp;
import org.example.demojavafx.domain.validators.UserValidator;

public class ServiciosUser {

    private final UserValidator userValidator;
    private final DaoUsers daoUsers;

    public ServiciosUser() {
        this.daoUsers = new DaoUsers();
        this.userValidator = new UserValidator();
    }

    public Either<ErrorApp,Boolean> loginUser(User user) {
        return userValidator.validateUser(user)
                .flatMap(ok -> daoUsers.getUserbyName(user.getUsername()))
                .flatMap(userDB -> {
                    if (userDB.getPassword().equals(user.getPassword()))
                    {
                        return Either.right(true);
                    } else {
                        return Either.left(new ErrorAppDatosNoValidos("Password incorrect"));
                    }
                });
    }

    public Either<ErrorApp,Boolean> registerUser(User user) {
        return userValidator.validateUser(user)
                .flatMap(ok -> userValidator.validatePassword(user))
                .flatMap(ok -> daoUsers.registerUser(user));

    }
}
