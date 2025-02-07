package org.example.backendspring.ui.config;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@EnableWebMvc
@Configuration
public class ConfigWeb {

//        @Bean
//        public MethodValidationPostProcessor methodValidationPostProcessor() {
//            return new MethodValidationPostProcessor();
//        }

    @Bean("JWT")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }


    @Bean("JWT2")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Key key2() {
        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update("clave".getBytes(StandardCharsets.UTF_8));
        final SecretKeySpec key2 = new SecretKeySpec(
                digest.digest(), 0, 64, "AES");
        SecretKey keyConfig = Keys.hmacShaKeyFor(key2.getEncoded());

        return keyConfig;
    }
}
