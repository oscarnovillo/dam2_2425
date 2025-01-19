package org.example.backendspring.errors;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
       super("no se encontro el recurso ");
    }
}
