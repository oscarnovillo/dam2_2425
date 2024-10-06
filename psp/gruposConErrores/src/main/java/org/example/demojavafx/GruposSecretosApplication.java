package org.example.demojavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GruposSecretosApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GruposSecretosApplication.class.getResource("/fxml/grupos.fxml"));
        Scene scene = new Scene((Parent) fxmlLoader.load(), 1024, 768);
        stage.setTitle("Grupos Secretos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
