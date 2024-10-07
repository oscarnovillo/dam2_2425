/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.springjavafx.common.seguridad.simetrico;


import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author oscar
 */
public class Main {

    public static void main(String[] args) {
        try {
            String texto = "pepe no tiene quien le eññññscriba, y quiero mnas mensaje";
            byte[] cifrado = PasswordHash.cifra("juan",texto);

            System.out.println(
                    new String(cifrado));

            String base64encode = Base64.getUrlEncoder().encodeToString(cifrado);
            System.out.println(base64encode);
            System.out.println(Base64.getEncoder().encodeToString(cifrado));


            byte[] base64decode = Base64.getUrlDecoder().decode(base64encode);
            base64decode = Base64.getUrlDecoder().decode(base64encode);
            System.out.println(new String(base64decode));


            System.out.println(PasswordHash.descifra("juan",base64decode));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
