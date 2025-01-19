package org.example.backendspring.errors;

public class ApiError {

    private String mensaje;

    public ApiError(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
