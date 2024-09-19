package org.example.springjavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;


public class DIJavafx extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        super.init();
        applicationContext = new SpringApplicationBuilder(SpringjavafxApplication.class).run();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setResizable(true);
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));

    }


    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
