package org.example.demojavafx.domain.errors;


import lombok.Data;

public record ErrorAppDatosNoValidos
(
        String message
) implements ErrorApp{}
