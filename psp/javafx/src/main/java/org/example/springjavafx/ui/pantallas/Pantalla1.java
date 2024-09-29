package org.example.springjavafx.ui.pantallas;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.example.springjavafx.domain.modelo.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Pantalla1 {


    private final PasswordEncoder passwordEncoder;




    public TextArea cifrado;
    public TextField txtNormal;
    public ListView<Usuario> usuarios;

    public Pantalla1( PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
        

    }

    public void cifrar(ActionEvent actionEvent) {

    }

    public void descrifrar(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion");
        alert.setHeaderText("Texto desencriptado");
        alert.setContentText("hola");
        alert.showAndWait();
    }

    public void crearCertificados(ActionEvent actionEvent) throws NoSuchAlgorithmException {







    }
}
