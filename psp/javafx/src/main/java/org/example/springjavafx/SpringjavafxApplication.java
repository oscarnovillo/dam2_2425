package org.example.springjavafx;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringjavafxApplication {

    public static void main(String[] args) {

        Application.launch(DIJavafx.class, args);

//        SpringApplication.run(SpringjavafxApplication.class, args);
    }

}
