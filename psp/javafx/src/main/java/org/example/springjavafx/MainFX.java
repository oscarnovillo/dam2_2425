package org.example.springjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.springjavafx.ui.pantallas.principal.PrincipalController;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class MainFX implements ApplicationListener<DIJavafx.StageReadyEvent> {


    private final FXMLLoader fxmlLoader;

    public MainFX(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }


    @Override
    public void onApplicationEvent(DIJavafx.StageReadyEvent event) {
        try {
//            ResourceBundle r = ResourceBundle.getBundle("/i18n/textos");
            Stage stage = event.getStage();
            //fxmlLoader.setResources(r);
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream("/fxml/principal.fxml"));
            PrincipalController controller = fxmlLoader.getController();


            stage.setScene(new Scene(fxmlParent));
            //stage.setMinHeight(1000);
//            stage.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
//            stage.setTitle(r.getString("app.title"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
