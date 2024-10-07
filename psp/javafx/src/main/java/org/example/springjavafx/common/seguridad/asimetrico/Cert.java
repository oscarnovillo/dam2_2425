/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.springjavafx.common.seguridad.asimetrico;


import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.crypto.Cipher;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author oscar
 */
public class Cert {

    public static void main(String[] args) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); // Hace uso del provider BC
        keyGen.initialize(2048,new SecureRandom());  // tamano clave 512 bits
        KeyPair clavesRSA = keyGen.generateKeyPair();
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        PublicKey clavePublica = clavesRSA.getPublic();

        // crear un certificado con CErtificateBuilder
        X500Name owner = new X500Name("CN=Oscar");
        X500Name issuer = new X500Name("CN=Servidor");
        X509v3CertificateBuilder certGen = new X509v3CertificateBuilder(
                issuer,
                BigInteger.valueOf(1),
                new Date(),
                new Date(System.currentTimeMillis()+1000000),
                owner, SubjectPublicKeyInfo.getInstance(
                ASN1Sequence.getInstance(clavePublica.getEncoded()))

        );

        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(clavePrivada);
        X509CertificateHolder certHolder = certGen.build(signer);
        X509Certificate cert3 = new JcaX509CertificateConverter().getCertificate(certHolder);


        cert3.verify(clavesRSA.getPublic());





        //crear un certificado de la clave publica
        //X509v3CertificateBuilder certGen = new X509v3CertificateBuilder();

        X509V3CertificateGenerator cert1 = new X509V3CertificateGenerator();
        cert1.setSerialNumber(BigInteger.valueOf(1));   //or generate a random number
        cert1.setSubjectDN(new X509Principal("CN=Oscar"));  //see examples to add O,OU etc
        cert1.setIssuerDN(new X509Principal("CN=Oscar")); //same since it is self-signed
        cert1.setPublicKey(clavesRSA.getPublic());
        cert1.setNotBefore(
                Date.from(LocalDate.now().plus(365, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC)));
        cert1.setNotAfter(new Date());
        cert1.setSignatureAlgorithm("SHA256WithRSAEncryption");
        PrivateKey signingKey = clavesRSA.getPrivate();


        X509Certificate cert =  cert1.generate(signingKey);

        //esto se puede mandar por la red
        String certificado = Base64.getUrlEncoder().encodeToString(cert.getEncoded());

        //lo recibimos y lo decodificamos
        byte[] certificadoDecodificado = Base64.getUrlDecoder().decode(certificado);

        //lo convertimos a certificado
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate certificadoRecibido = (X509Certificate)cf.generateCertificate(new ByteArrayInputStream(certificadoDecodificado));

        // comprobamos que el certificado recibido esta firmado por la clave privada anterior
        certificadoRecibido.verify(clavesRSA.getPublic());




//        cert.verify(clavePublica);
//        X500Name x500Name = new X500Name("CN=***.com, OU=Security&Defense, O=*** Crypto., L=Ottawa, ST=Ontario, C=CA");
//        SubjectPublicKeyInfo pubKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
//        final Date start = new Date();
//        final Date until = Date.from(LocalDate.now().plus(365, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC));
//        final X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(x500Name,
//                new BigInteger(10, new SecureRandom()), start,   until,  x500Name,  pubKeyInfo
//        );
//        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate());
//
//        Certificate certificate = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider()).getCertificate(certificateBuilder.build(contentSigner));
//
//        System.out.println("x.509 certificate is successfully generated!");

//        X509Certificate caCert = X509CertificateBuilder.fromKeyPair(ca)
//                .subject(new X500Principal("CN=ca"))
//                .validity(Instant.now(), Instant.now().plus(Period.ofDays(3650)))
//                .addExtension("BasicConstraints", true, "")
//                .signatureAlgorithm("SHA256withRSA")
//                .selfSign();
        try {
            // Anadir provider JCE (provider por defecto no soporta RSA)
//            Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
//
//                    final X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(x500Name,
//                new BigInteger(10, new SecureRandom()), start,   until,  x500Name,  pubKeyInfo
//        );
//            CertAndKeyGen certGen = new CertAndKeyGen("RSA", "SHA256WithRSA", null);
//            // generate it with 2048 bits
//            certGen.generate(2048);
//            // prepare the validity of the certificate
//            long validSecs = (long) 365 * 24 * 60 * 60; // valid for one year
//            // add the certificate information, currently only valid for one year.
//
//            X509Certificate cert = certGen.getSelfCertificate(
//                    // enter your details according to your application
//                    new X500Name("CN=Pedro Salazar,O=My Organisation,L=My City,C=DE"), validSecs);



            PrivateKey pk = clavesRSA.getPrivate();
            PublicKey publicKey = clavesRSA.getPublic();

            System.out.println("********************************************************");
            System.out.println(cert3.getIssuerX500Principal());
            System.out.println(cert3.getSubjectX500Principal());

            String dn = cert.getSubjectX500Principal().getName();

            System.out.println("********************************************************");

            LdapName ldapDN = new LdapName(dn);
            for (Rdn rdn : ldapDN.getRdns()) {
                if (rdn.getType().equals("CN")) {
                    System.out.println(rdn.getValue());
                }
            }

            //KeyPairGenerator generadorRSA = KeyPairGenerator.getInstance("RSA", "BC"); // Hace uso del provider BC
            //generadorRSA.initialize(1024);
//            KeyPair clavesRSA = null;
//            PrivateKey clavePrivada = null;
//            PublicKey clavePublica = null;

            KeyStore ks = KeyStore.getInstance("PKCS12");
            //esta password se debería hashear
            char[] password = "abc".toCharArray();
            ks.load(null, null);
            ks.setCertificateEntry("publica", cert);
            ks.setKeyEntry("privada", pk, password, new Certificate[]{cert});
            ks.setKeyEntry("privada2", pk, password, new Certificate[]{cert});
            FileOutputStream fos = new FileOutputStream("keystore.pfx");
            ks.store(fos, password);
            fos.close();



            //leer fichero
            KeyStore ksLoad = KeyStore.getInstance("PKCS12");
            ksLoad.load(new FileInputStream("keystore.pfx"), password);

            X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate("publica");
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password);
            PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) ksLoad.getEntry("privada", pt);
            PrivateKey keyLoad = (PrivateKey) privateKeyEntry.getPrivateKey();

            System.out.println(certLoad.getIssuerX500Principal());
            System.out.println(certLoad.getSubjectX500Principal());
            //certLoad.verify(clavePublica);

            dn = certLoad.getSubjectX500Principal().getName();
            ldapDN = new LdapName(dn);
            for (Rdn rdn : ldapDN.getRdns()) {
                if (rdn.getType().equals("CN")) {
                    System.out.println(rdn.getValue());
                }
            }

            clavesRSA = new KeyPair(certLoad.getPublicKey(), keyLoad);

            Cipher cifrador = Cipher.getInstance("RSA");
            cifrador.init(Cipher.ENCRYPT_MODE, keyLoad);
            cifrador.doFinal("hola".getBytes());

            Signature sign = Signature.getInstance("SHA256WithRSA");

            sign.initSign(keyLoad);

            MessageDigest hash = MessageDigest.getInstance("SHA-512");

            sign.update(hash.digest("hola".getBytes()));
            byte[] firma = sign.sign();

            sign.initVerify(certLoad.getPublicKey());
            sign.update(hash.digest("hola".getBytes()));
            System.out.println(sign.verify(firma));

            try {
                CertificateFactory cf1 = CertificateFactory.getInstance("X.509");
                Certificate cert2 = cf1.generateCertificate(new ByteArrayInputStream(certLoad.getEncoded()));

                System.out.println(cert2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }



        } catch (Exception ex) {
            Logger.getLogger(Cert.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
