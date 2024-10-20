package org.example.springjavafx.domain.usecases;

import org.example.springjavafx.common.seguridad.asimetrico.BouncyCastleCertificateWithBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service

public class CreateCertificateUseCase {


    @Async
    public CompletableFuture<Void> execute() throws InterruptedException {
        Thread.sleep(3000);
        Random rd = new Random();
        if (rd.nextBoolean()) {
            throw new RuntimeException("Error al crear certificado");
        }
        BouncyCastleCertificateWithBuilder.main(new String[]{});
        return CompletableFuture.completedFuture(null);
    }
}
