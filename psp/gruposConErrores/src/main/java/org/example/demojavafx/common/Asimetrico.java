package org.example.demojavafx.common;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.util.Date;

public class Asimetrico {

    private Configuracion configuracion;

    public Object generarYGuardarClavesUsuario() {
        // TODO implement here
        // Generar un par de claves usando ECDSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp521r1"));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Generar el certificado autofirmado
        X509Certificate certificate = generateSignedCertificate(keyPair,dameClavePrivada("servidor", configuracion.getPasswordKeyStore()));

    }

    public Void dameClavePrivada(String nombre,String password) {
        // TODO implement here


        return null;
    }

    public Void dameClavePublica(String nombre) {
        // TODO implement here

        return null;
    }


    // Método para generar un certificado autofirmado usando X509v3CertificateBuilder
    private static X509Certificate generateSignedCertificate(KeyPair keyPair,PrivateKey keyCA)
            throws Exception {
        // Configurar el DN del emisor y el sujeto
        X500Name issuer = new X500Name("CN=Test Certificate");
        X500Name subject = issuer;

        // Definir el periodo de validez del certificado
        Date notBefore = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24); // Un día antes
        Date notAfter = new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365); // Válido por un año

        // Número de serie único
        BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());

        // Crear el constructor del certificado
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
        X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                issuer, serialNumber, notBefore, notAfter, subject, subjectPublicKeyInfo
        );

        // Crear el firmante de contenido para firmar el certificado con la clave privada
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withECDSA")
                .setProvider("BC")
                .build(keyPair.getPrivate());

        // Generar el certificado
        X509CertificateHolder certificateHolder = certificateBuilder.build(contentSigner);

        // Convertir el certificado de BouncyCastle a X509Certificate (clase estándar de Java)
        return new JcaX509CertificateConverter()
                .setProvider("BC")
                .getCertificate(certificateHolder);
    }

}
