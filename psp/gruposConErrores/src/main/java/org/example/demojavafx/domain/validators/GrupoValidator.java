package org.example.demojavafx.domain.validators;

import io.vavr.control.Either;
import org.example.demojavafx.domain.modelo.Grupo;
import org.example.demojavafx.domain.modelo.User;

public class GrupoValidator {

    public Either<String,Boolean> validateGrupo(Grupo user){
        boolean ok = true;

        if (user.getNombre().isBlank() || user.getNombre().isEmpty()) {
            return Either.left( "nombre no valido");
        }
        else if (user.getPassword().isBlank() || user.getPassword().isEmpty()) {
            return Either.left("password no valido");
        }
        return Either.right(true);
    }

}
