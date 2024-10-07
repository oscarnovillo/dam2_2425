/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.springjavafx.common.seguridad.asimetrico;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oscar
 */
public class SignTest {
       public static void main(String[] args) throws FileNotFoundException {

        String nombre = "tomas";
        try {
            // Anadir provider JCE (provider por defecto no soporta RSA)
            // Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
            //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cifrador = Cipher.getInstance("RSA");


            // Crear KeyFactory (depende del provider) usado para las transformaciones de claves*/
            KeyFactory keyFactoryRSA = KeyFactory.getInstance("RSA"); // Hace uso del provider BC
            //*** 4 Recuperar clave PUBLICA del fichero */
            // 4.1 Leer datos binarios x809
            byte[] bufferPub = new byte[5000];
            FileInputStream in = new FileInputStream(nombre + ".publica");
            DataInputStream d = new DataInputStream(in);

            int charsPub = in.read(bufferPub, 0, 5000);
            in.close();

            byte[] bufferPub2 = new byte[charsPub];
            System.arraycopy(bufferPub, 0, bufferPub2, 0, charsPub);


            // 4.2 Recuperar clave publica desde datos codificados en formato X509
            X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferPub2);
            PublicKey clavePublica2 = keyFactoryRSA.generatePublic(clavePublicaSpec);


            // PASO 3b: Poner cifrador en modo DESCIFRADO
            // 2 Recuperar clave Privada del fichero */
            // 2.1 Leer datos binarios PKCS8
            byte[] bufferPriv = new byte[5000];
            in = new FileInputStream(nombre + ".privada");
            int chars = in.read(bufferPriv, 0, 5000);
            in.close();

            byte[] bufferPriv2 = new byte[chars];
            System.arraycopy(bufferPriv, 0, bufferPriv2, 0, chars);

            // 2.2 Recuperar clave privada desde datos codificados en formato PKCS8
            PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(bufferPriv2);

            PrivateKey clavePrivada2 = keyFactoryRSA.generatePrivate(clavePrivadaSpec);


            Signature sign = Signature.getInstance("SHA256WithRSA");
            sign.initSign(clavePrivada2);
            sign.update("contenIdo".getBytes());
            byte[] firma = sign.sign();

            sign.initVerify(clavePublica2);
            sign.update("contenido".getBytes());
            System.out.println(sign.verify(firma));

        } catch (Exception ex) {
            Logger.getLogger(CifrarRSAFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
