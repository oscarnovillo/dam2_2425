package org.example.demojavafx.data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.vavr.control.Either;
import org.example.demojavafx.common.Configuracion;
import org.example.demojavafx.domain.errors.ErrorAppDataBase;
import org.example.demojavafx.domain.errors.ErrorApp;

import org.example.demojavafx.domain.modelo.Grupo;
import org.example.demojavafx.domain.modelo.Mensaje;
import org.example.demojavafx.domain.modelo.User;


import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DataBase {


    private final Gson gson;

    private final Configuracion configuracion;

    public DataBase() {



        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->
                                LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.toString()))

                .create();


        this.configuracion = Configuracion.getInstance();
    }




    public Either<ErrorApp,List<User>> loadUsuarios() {
        Type userListType = new TypeToken<ArrayList<User>>() {
        }.getType();

        List<User> usuarios = null;
        try {
            usuarios = gson.fromJson(
                    new FileReader(configuracion.getPathJsonUsuarios()),
                    userListType);
            if (usuarios == null) {
                usuarios = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(usuarios);
    }

    public boolean saveUsuarios(List<User> usuarios) {

        try (FileWriter w = new FileWriter(configuracion.getPathJsonUsuarios())) {
            gson.toJson(usuarios, w);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public Either<String,List<Grupo>> loadGrupos() {
        Type grupoListType = new TypeToken<ArrayList<Grupo>>() {
        }.getType();

        List<Grupo> grupos = null;
        try {
            grupos = gson.fromJson(
                    new FileReader(configuracion.getPathJsonGrupos()),
                    grupoListType);
            if (grupos == null) {
                grupos = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return Either.left("grupos error");
        }
        return Either.right(grupos);
    }

    public Either<String,Boolean> saveGrupos(List<Grupo> grupos) {

        try (FileWriter w = new FileWriter(configuracion.getPathJsonGrupos())) {
            gson.toJson(grupos, w);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left("grupos error");
        }

        return Either.right(true);
    }

    public List<Mensaje> loadMensajes() {
        Type mensajeListType = new TypeToken<ArrayList<Mensaje>>() {
        }.getType();

        List<Mensaje> mensajes = null;
        try {
            mensajes = gson.fromJson(
                    new FileReader(configuracion.getPathJsonMensajes()),
                    mensajeListType);
            if (mensajes == null) {
                mensajes = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return mensajes;
    }

    public boolean saveMensajes(List<Mensaje> mensajes) {

        try (FileWriter w = new FileWriter(configuracion.getPathJsonMensajes())) {
            gson.toJson(mensajes, w);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

}
