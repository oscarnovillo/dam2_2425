package org.example.springjavafx.domain.modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteVip extends Cliente{

    private Double descuento;


    public ClienteVip(String nombre, String dni, Double descuento) {
        super(nombre, dni);
        this.descuento= descuento;
        type = "ClienteVip";

    }



    @Override
    public String toString() {
        return "ClienteVip{" +
                "descuento=" + descuento +
                '}'+super.toString();
    }
}
