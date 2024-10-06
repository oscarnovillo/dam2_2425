package org.example.demojavafx.domain.validators;

import io.vavr.control.Either;
import org.example.demojavafx.domain.errors.ErrorApp;
import org.example.demojavafx.domain.errors.ErrorAppDatosNoValidos;
import org.example.demojavafx.domain.modelo.User;

public class UserValidator {


    public Either<ErrorApp,Boolean> validateUser(User user){
        if (user.getUsername().isBlank() || user.getUsername().isEmpty()) {
            return Either.left(new ErrorAppDatosNoValidos("nombre no valido"));
        }
        return Either.right(true);
    }

    public Either<ErrorApp,Boolean> validatePassword(User user){


        if (user.getPassword().isBlank() || user.getPassword().isEmpty()) {
            return Either.left(new ErrorAppDatosNoValidos("password no valido"));
        }


        return Either.right(true);
    }
}
