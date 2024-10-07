package org.example.springjavafx.common.seguridad.asimetrico;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

// Necesario para usar el provider Bouncy Castle (BC)
//    Para compilar incluir el fichero JAR en el classpath
//
public class EjemploRSA  {
   public static void main (String[] args) throws Exception {
      //Security.addProvider(new BouncyCastleProvider());
      //Anadir provider JCE (provider por defecto no soporta RSA)
      //Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC

      System.out.println("1. Creando claves publica y privada");

      // PASO 1: Crear e inicializar el par de claves RSA DE 512 bits
      KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); // Hace uso del provider BC



       KeyPair clavesRSA = keyGen.generateKeyPair();
       PrivateKey clavePrivada = clavesRSA.getPrivate();
       PublicKey clavePublica = clavesRSA.getPublic();
       //maximo de caracteres keylength/8 - Padding (11 o  0)
       System.out.print("2. Introducir Texto Plano (max. 64 caracteres): ");
      byte[] bufferPlano = leerLinea(System.in);

      // PASO 2: Crear cifrador RSA

      Cipher cifrador = Cipher.getInstance("RSA");

      /************************************************************************
       * IMPORTANTE: En BouncyCastle el algoritmo RSA no funciona realmente en modo ECB
       *		  * No divide el mensaje de entrada en bloques
       *                  * Solo cifra los primeros 512 bits (tam. clave)
       *		  * Para cifrar mensajes mayores, habría que hacer la
       *                    división en bloques "a mano"
       ************************************************************************/


      // PASO 3a: Poner cifrador en modo CIFRADO
      cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);  // Cifra con la clave publica



       System.out.println("3a. Cifrar con clave publica");
      byte[] bufferCifrado = cifrador.doFinal(bufferPlano);
      System.out.println("TEXTO CIFRADO");

      mostrarBytes(bufferCifrado);
      System.out.println("\n-------------------------------");

      // PASO 3b: Poner cifrador en modo DESCIFRADO
      cifrador.init(Cipher.DECRYPT_MODE, clavePrivada); // Descrifra con la clave privada

      System.out.println("3b. Descifrar con clave privada");
      byte[] bufferPlano2 = cifrador.doFinal(bufferCifrado);
      System.out.println("TEXTO DESCIFRADO");
      mostrarBytes(bufferPlano2);
      System.out.println("\n-------------------------------");

      // PASO 3a: Poner cifrador en modo CIFRADO
      cifrador.init(Cipher.ENCRYPT_MODE, clavePrivada);  // Cifra con la clave publica


      System.out.println("4a. Cifrar con clave privada");
      bufferCifrado = cifrador.doFinal(bufferPlano);
      System.out.println("TEXTO CIFRADO");
      mostrarBytes(bufferCifrado);
      System.out.println("\n-------------------------------");


      // PASO 3b: Poner cifrador en modo DESCIFRADO
      cifrador.init(Cipher.DECRYPT_MODE, clavePublica); // Descrifra con la clave privada

      System.out.println("4b. Descifrar con clave publica");
      bufferPlano2 = cifrador.doFinal(bufferCifrado);
      System.out.println("TEXTO DESCIFRADO");
      mostrarBytes(bufferPlano2);
      System.out.println("\n--------1234567890-----------------------");
   } // Fin main


   public static byte[] leerLinea(InputStream in) throws IOException {
      byte[] buffer1 = new byte[1000];
      int i =0;
      byte c;
      c = (byte) in.read();
      while((c != '\n') && (i <1000)) {
	 buffer1[i] = c;
	 c = (byte) in.read();
	 i++;
      }

      byte[] buffer2 = new byte[i];
      for(int j=0; j <i; j++) {
	 buffer2[j]=buffer1[j];
      }
      return(buffer2);
   }

   public static void mostrarBytes(byte [] buffer) {
		System.out.write(buffer, 0, buffer.length);
   }

} // Fin clase

