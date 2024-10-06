package org.example.demojavafx.data;

import io.vavr.control.Either;
import org.example.demojavafx.domain.modelo.Grupo;
import org.example.demojavafx.domain.modelo.User;

import java.util.List;
import java.util.stream.Collectors;

public class DaoGrupos {

    private final DataBase dataBase;

    public DaoGrupos() {
        this.dataBase = new DataBase();
    }


    public Either<String,Grupo> getGrupoByName(String grupoName) {
        return dataBase.loadGrupos()
                .flatMap(grupos->grupos.stream()
                        .filter(grupo -> grupo.getNombre().equals(grupoName))
                        .findFirst()
                        .map(Either::<String, Grupo>right)
                        .orElseGet(() -> Either.left("Group not found: " + grupoName)));

    }

    public Either<String, List<Grupo>> getGruposOfUser(User user) {
        return dataBase.loadGrupos()
                .map(grupos -> grupos.stream()
                        .filter(grupo -> grupo.getUsuarios().contains(user))
                        .collect(Collectors.toList())
                )
                .flatMap(grupoList -> {
                    if (grupoList.isEmpty()) {
                        return Either.left("No groups found for user: " + user.getUsername());
                    } else {
                        return Either.right(grupoList);
                    }
                });
    }


    public Either<String, Boolean> addUserToGrupo(Grupo grupo, User user) {

        return dataBase.loadGrupos()
                .flatMap(grupos -> {
                    // Buscar el grupo en la lista de grupos
                    Either<String, Grupo> grupoEither = grupos.stream()
                            .filter(g -> g.getNombre().equals(grupo.getNombre()))
                            .findFirst()
                            .map(Either::<String, Grupo>right)
                            .orElseGet(() -> Either.left("Group not found: " + grupo.getNombre()));

                    // Agregar el usuario si el grupo fue encontrado
                    return grupoEither.flatMap(grupoDB -> {
                        grupoDB.getUsuarios().add(user);
                        return dataBase.saveGrupos(grupos);
                    });
                });

    }

    public Either<String, Boolean> registerGrupo(Grupo grupoNuevo) {

        return dataBase.loadGrupos()
                .flatMap(grupos -> {
                    if (grupos.stream().anyMatch(grupo -> grupo.getNombre().equals(grupoNuevo.getNombre()))) {
                        return Either.left("Group already exists: " + grupoNuevo.getNombre());
                    } else {
                        return Either.right(grupos);
                    }
                })
                .flatMap(grupos -> {
                    grupos.add(grupoNuevo);
                    return dataBase.saveGrupos(grupos);
                });
    }

}
