package org.example.springjavafx.ui.pantallas;

import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import org.example.springjavafx.common.seguridad.asimetrico.BouncyCastleCertificateWithBuilder;
import org.example.springjavafx.domain.modelo.Usuario;
import org.example.springjavafx.domain.servicios.ServiciosUsers;
import org.example.springjavafx.domain.usecases.CreateCertificateUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class Pantalla1 {


    private final PasswordEncoder passwordEncoder;
    private final CreateCertificateUseCase createCertificateUseCase;
    private final ServiciosUsers serviciosUsers;


    public TextArea cifrado;
    public TextField txtNormal;
    public ListView<Usuario> usuarios;
    public AnchorPane pane;

    public Pantalla1(PasswordEncoder passwordEncoder, CreateCertificateUseCase createCertificateUseCase, ServiciosUsers serviciosUsers) {

        this.passwordEncoder = passwordEncoder;


        this.createCertificateUseCase = createCertificateUseCase;
        this.serviciosUsers = serviciosUsers;
    }

    public void cifrar(ActionEvent actionEvent) {
        pane.setCursor(javafx.scene.Cursor.DEFAULT);
    }

    public void descrifrar(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion");
        alert.setHeaderText("Texto desencriptado");
        alert.setContentText("hola");
        alert.showAndWait();
    }

    public void login(ActionEvent actionEvent) {
        pane.setCursor(javafx.scene.Cursor.WAIT);

        serviciosUsers.login(new Usuario("aitor", "1234"))
          .thenAccept(usuario -> {
            usuario.peek(u -> System.out.println(u))
                            .peekLeft(e -> System.out.println(e.getMessage()));


            System.out.println("usuario logeado");
            pane.setCursor(Cursor.DEFAULT);
        }).exceptionally(throwable -> {
            System.out.println("error");
            pane.setCursor(Cursor.DEFAULT);
            return null;
        });

        pane.setCursor(Cursor.DEFAULT);
    }

    public void crearCertificados(ActionEvent actionEvent) throws NoSuchAlgorithmException, InterruptedException, ExecutionException {
        pane.setCursor(javafx.scene.Cursor.WAIT);


       // createCertificateUseCase.execute().complete("Certificados creados");
        createCertificateUseCase.execute().thenRunAsync(() -> {
            System.out.println("fin");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    pane.setCursor(javafx.scene.Cursor.DEFAULT);
                    System.out.println("fin");
            txtNormal.setText("Certificados creados");

        }
        ).handleAsync((aVoid, throwable) -> {
            System.out.println("handle");
            return "hola";
        });










    }
}
