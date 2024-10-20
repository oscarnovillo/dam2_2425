package org.example.springjavafx.domain.usecases;

import org.example.springjavafx.common.seguridad.asimetrico.BouncyCastleCertificateWithBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service

public class CreateCertificateUseCase {

    @Async
    public void execute() throws InterruptedException {
        Thread.sleep(3000);
        BouncyCastleCertificateWithBuilder.main(new String[]{});
    }
}
