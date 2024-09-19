package org.example.springjavafx.dao;

import org.example.springjavafx.domain.modelo.Cliente;

import java.util.List;

public interface DaoClientes {
    boolean updateCliente(Cliente c);

    boolean addCliente(Cliente c);

    List<Cliente> getClientes();
}
