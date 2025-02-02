package org.example.springjavafx.ui.pantallas.principal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.example.springjavafx.domain.servicios.ServiciosUsers;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PrincipalController {

    private final ServiciosUsers serviciosUsers;
    private final ApplicationContext context;
    @FXML
    public BorderPane root;

    public PrincipalController(ServiciosUsers serviciosUsers, ApplicationContext context) {
        this.serviciosUsers = serviciosUsers;
        this.context = context;
    }


    public void initialize() {
        System.out.println(serviciosUsers.getNombreUsuario());
        cargarPantalla("/fxml/pantalla1.fxml");
    }




    private Pane cargarPantalla(String ruta) {
        Pane panePantalla = null;
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(context::getBean);
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(ruta));
            root.setCenter(panePantalla);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return panePantalla;
    }

}
