package org.example.demojavafx.domain.errors;


import lombok.Data;


public sealed interface ErrorApp
        permits ErrorAppDataBase, ErrorAppDatosNoValidos{}
