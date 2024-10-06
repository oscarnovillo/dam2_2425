package org.example.demojavafx.ui;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.demojavafx.domain.errors.ErrorApp;
import org.example.demojavafx.domain.errors.ErrorAppDataBase;
import org.example.demojavafx.domain.errors.ErrorAppDatosNoValidos;
import org.example.demojavafx.domain.modelo.Grupo;
import org.example.demojavafx.domain.modelo.Mensaje;
import org.example.demojavafx.domain.modelo.User;
import org.example.demojavafx.domain.servicios.ServiciosGrupo;
import org.example.demojavafx.domain.servicios.ServiciosMensaje;
import org.example.demojavafx.domain.servicios.ServiciosUser;

import java.util.ArrayList;

public class GruposController {
    private final Alert alertError;
    private final Alert alertInfo;
    private final ServiciosUser serviciosUser;
    private final ServiciosGrupo serviciosGrupo;
    private final ServiciosMensaje serviciosMensaje;
    @FXML
    private ListView<Mensaje> mensajesGrupo;
    @FXML
    private TextField txtContenidoMensaje;
    @FXML
    private Label txtGrupoSeleccionado;
    @FXML
    private ListView<Grupo> gruposUsuario;

    @FXML
    private TextField txtNombreGrupoAdd;
    @FXML
    private TextField txtPassGrupoAdd;
    @FXML
    private TextField txtNombreGrupoEntrar;
    @FXML
    private TextField txtPasswordGrupoEntrar;
    @FXML
    private TextField txtLoginUser;
    @FXML
    private TextField txtPasswordUser;
    @FXML
    private TextField txtRegistroUser;
    @FXML
    private TextField txtRegistroPassword;
    @FXML
    private Label txtUsuarioLogueado;

    private User userLogueado;
    private Grupo grupoSeleccionado;

    public GruposController() {
        this.alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setTitle("Info");
        alertInfo.setHeaderText("Info");
        this.alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error");
        alertError.setHeaderText("Error");

        this.serviciosUser = new ServiciosUser();
        this.serviciosGrupo = new ServiciosGrupo();
        this.serviciosMensaje = new ServiciosMensaje();
    }


    public void initialize() {
        gruposUsuario.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Grupo>) c -> {
            Grupo grupo = gruposUsuario.getSelectionModel().getSelectedItem();
            if (grupo != null) {
                grupoSeleccionado = grupo;
                txtGrupoSeleccionado.setText(grupo.getNombre());
                refrescarMensajesGrupo();
            }

        });
    }

    private void mostrarInfo(String infoText) {
        alertInfo.setContentText(infoText);
        alertInfo.showAndWait();
    }

    private void mostratError(ErrorApp errorText) {
        String errorMensaje = "";
        switch(errorText)
        {
            case ErrorAppDataBase e -> {
                switch(e)
                {
                    case TIMEOUT -> errorMensaje = "Error en la base de datos";
                    case NO_CONNECTION -> errorMensaje = "Error en la base de datos";
                }

            }
            case ErrorAppDatosNoValidos e -> errorMensaje = e.message();
        }

        alertError.setContentText(errorMensaje);
        alertError.showAndWait();
    }
    private void mostratError(String errorText) {
        alertError.setContentText(errorText);
        alertError.showAndWait();
    }


    private void cargarGruposUsuario() {
        serviciosGrupo.getGruposOfUser(userLogueado).peek(grupos -> {
            gruposUsuario.getItems().clear();
            gruposUsuario.getItems().addAll(grupos);
            grupoSeleccionado = null;
            txtGrupoSeleccionado.setText("");
        }).peekLeft(this::mostratError);
    }

    @FXML
    private void loginUser(ActionEvent actionEvent) {
        User loginUser = new User(this.txtLoginUser.getText(), this.txtPasswordUser.getText());
        serviciosUser.loginUser(loginUser).peek(ok ->{
            userLogueado = loginUser;
            txtUsuarioLogueado.setText("Bienvenido " + loginUser.getUsername());
            cargarGruposUsuario();
        }).peekLeft(this::mostratError);

    }

    @FXML
    private void registerUser(ActionEvent actionEvent) {
        User registerUser = new User(this.txtRegistroUser.getText(), this.txtRegistroPassword.getText());
        serviciosUser.registerUser(registerUser)
                .peek(ok -> mostrarInfo("Usuario " + registerUser.getUsername() + "registrado"))
                .peekLeft(this::mostratError);
    }

    @FXML
    private void addGrupo(ActionEvent actionEvent) {

        Grupo grupo = new Grupo(txtNombreGrupoAdd.getText(), txtPassGrupoAdd.getText(), new ArrayList<>());
        serviciosGrupo.registerGrupo(grupo)
                .peek(ok -> mostrarInfo("Grupo " + grupo.getNombre() + " creado"))
                .peekLeft(this::mostratError);

    }

    @FXML
    private void entrarEnGrupo(ActionEvent actionEvent) {
        Grupo grupo = new Grupo(txtNombreGrupoEntrar.getText(), txtPasswordGrupoEntrar.getText(), new ArrayList<>());
       serviciosGrupo.entrarEnGrupo(grupo, userLogueado).peek(ok -> {
            mostrarInfo("Usuario " + userLogueado.getUsername() + " añadido al grupo " + grupo.getNombre());
            cargarGruposUsuario();
        }).peekLeft(this::mostratError);
    }

    public void addMensajeGrupo(ActionEvent actionEvent) {
        if (grupoSeleccionado != null) {
            Mensaje m = new Mensaje(txtContenidoMensaje.getText(), grupoSeleccionado);
            serviciosMensaje.addMensajeToGrupo(m);
            refrescarMensajesGrupo();

        } else {
            mostratError("Selecciona un grupo");
        }
    }

    public void refrescarMensajesGrupo() {
        if (grupoSeleccionado != null) {
            mensajesGrupo.getItems().clear();
            mensajesGrupo.getItems().addAll(serviciosMensaje.getMensajesOfGrupo(grupoSeleccionado.getNombre()));
        } else {
            mostratError("Selecciona un grupo");
        }
    }
}
