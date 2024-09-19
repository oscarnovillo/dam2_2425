package org.example.springjavafx;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public FXMLLoader createLoader(ApplicationContext context) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        return loader;
    }

    @Bean
    public PasswordEncoder createPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
