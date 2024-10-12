package org.example.springjavafx.common.seguridad.asimetrico;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.util.Date;

public class BouncyCastleCertificateWithBuilder {

    static {
        // Registrar BouncyCastle como proveedor de seguridad
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) {
        try {
            // Generar un par de claves usando ECDSA
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
            keyPairGenerator.initialize(new ECGenParameterSpec("secp521r1"));
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Generar el certificado autofirmado
            X509Certificate certificate = generateSelfSignedCertificate(keyPair);

            // Crear un keystore de tipo JKS
            KeyStore keyStore = KeyStore.getInstance("JKS");
            char[] password = "password".toCharArray(); // Contraseña para proteger el keystore
            keyStore.load(null, password);

            // Guardar la clave privada y el certificado en el keystore
            keyStore.setKeyEntry("alias2", keyPair.getPrivate(), password, new Certificate[]{certificate});

            // Guardar el keystore en un archivo
            try (FileOutputStream fos = new FileOutputStream("keystore.jks")) {
                keyStore.store(fos, password);
            }

            System.out.println("Keystore creado con éxito, incluyendo clave privada y certificado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para generar un certificado autofirmado usando X509v3CertificateBuilder
    private static X509Certificate generateSelfSignedCertificate(KeyPair keyPair)
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
