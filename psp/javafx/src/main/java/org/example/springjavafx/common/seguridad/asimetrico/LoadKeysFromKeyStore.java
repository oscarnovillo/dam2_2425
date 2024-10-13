package org.example.springjavafx.common.seguridad.asimetrico;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;

public class LoadKeysFromKeyStore {

    public static void main(String[] args) {
        try {
            // Cargar el keystore
            FileInputStream fis = new FileInputStream("keystore.jks");
            KeyStore keyStore = KeyStore.getInstance("JKS");
            char[] password = "password".toCharArray();  // Contraseña para proteger el keystore
            keyStore.load(fis, password);

            // Recuperar la clave privada usando el alias
            String alias = "alias";
            KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(password);
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, entryPassword);

            // Obtener la clave privada
//            PrivateKey privateKey = privateKeyEntry.getPrivateKey();
//            System.out.println("Clave privada: " + java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded()));

            // Obtener la clave pública desde el certificado asociado
            Certificate certificate = keyStore.getCertificate(alias);
            Certificate certificate2 = keyStore.getCertificate("alias2");

            PublicKey publicKey = certificate2.getPublicKey();
            ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
            ECParameterSpec ecParams = ecPublicKey.getParams();
            System.out.println(ecPublicKey.getAlgorithm()+" "+ecParams.getOrder()+" "+ecParams.getCurve());
            System.out.println("Clave pública: " + java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
