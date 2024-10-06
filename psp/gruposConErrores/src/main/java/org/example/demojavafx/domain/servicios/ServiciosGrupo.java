package org.example.demojavafx.domain.servicios;

import io.vavr.control.Either;
import org.example.demojavafx.data.DaoGrupos;
import org.example.demojavafx.domain.modelo.Grupo;
import org.example.demojavafx.domain.modelo.User;
import org.example.demojavafx.domain.validators.GrupoValidator;

import java.util.List;

public class ServiciosGrupo {

    private final DaoGrupos daoGrupos;
    private final GrupoValidator grupoValidator;

    public ServiciosGrupo() {
        this.daoGrupos = new DaoGrupos();
        grupoValidator = new GrupoValidator();
    }

    private Either<String, Boolean> comprobarContraseña(Grupo grupo) {
        if (grupo.getPassword().equals(grupo.getPassword())) {
            return Either.right(true);
        }
        return Either.left("Nombre contraseña erronea");

    }

    public Either<String, Boolean> entrarEnGrupo(Grupo grupo, User user) {
        return daoGrupos.getGrupoByName(grupo.getNombre())
                .flatMap(this::comprobarContraseña)
                .flatMap(ok -> daoGrupos.addUserToGrupo(grupo, user));


    }

    public Either<String, Boolean> registerGrupo(Grupo grupo) {
        return grupoValidator.validateGrupo(grupo)
                .flatMap(ok -> daoGrupos.registerGrupo(grupo));
    }

    public Either<String, List<Grupo>> getGruposOfUser(User user) {
        return daoGrupos.getGruposOfUser(user);
    }

}
