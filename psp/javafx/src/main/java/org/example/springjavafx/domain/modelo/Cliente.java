package org.example.springjavafx.domain.modelo;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Getter
public abstract class Cliente {

    public String type;

    private String dni;
    private String nombre;
    private List<String> productos;


    public Cliente(String nombre, String dni) {
        this.dni = dni;
        this.nombre = nombre;
        productos = new ArrayList<>();

    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(dni, cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ",producto" + productos +
                '}';
    }
}
