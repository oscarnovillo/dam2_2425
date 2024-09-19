package org.example.springjavafx.domain.modelo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteNormal extends Cliente{



    public ClienteNormal(String nombre, String dni) {
        super(nombre, dni);
        type = "ClienteNormal";
    }



    @Override
    public String toString() {
        return "ClienteNormal{}"+super.toString();
    }
}
