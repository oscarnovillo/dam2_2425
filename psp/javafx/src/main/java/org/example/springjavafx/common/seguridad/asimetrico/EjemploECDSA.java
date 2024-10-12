package org.example.springjavafx.common.seguridad.asimetrico;

import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class EjemploECDSA {





    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC"); // Hace uso del provider BC
        keyGen.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
        //keyGen.initialize(512);
        // Generar el par de claves
        KeyPair keyPair2 = keyGen.generateKeyPair();
        X9ECParameters ecp = SECNamedCurves.getByName("secp256r1");
        ECDomainParameters domainParams = new ECDomainParameters(ecp.getCurve(),
                ecp.getG(), ecp.getN(), ecp.getH(),
                ecp.getSeed());


        // Generate a private key and a public key
        AsymmetricCipherKeyPair keyPair;
        ECKeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(domainParams, new SecureRandom());
        ECKeyPairGenerator generator = new ECKeyPairGenerator();
        generator.init(keyGenParams);
        keyPair = generator.generateKeyPair();


        ECPrivateKeyParameters privateKey = (ECPrivateKeyParameters) keyPair.getPrivate();
        ECPublicKeyParameters publicKey = (ECPublicKeyParameters) keyPair.getPublic();
        byte[] privateKeyBytes = privateKey.getD().toByteArray();
        byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);

        // Convert the public key to a format that can be used by Java
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKeyJava = keyFactory.generatePublic(publicKeySpec);
        //Convert the private key to a format that can be used by Java
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKeyJava = keyFactory.generatePrivate(privateKeySpec);



        PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        //java.security.spec.ECPrivateKeySpec clavePrivadaSpec = new java.security.spec.ECPrivateKeySpec(BigInteger.ONE,privateKeyBytes);
        X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(publicKeyBytes);
        /**
         * * Crear KeyFactory (depende del provider) usado para las
         * transformaciones de claves
         */
        KeyFactory keyFactoryEC = KeyFactory.getInstance("EC");

        PrivateKey clavePrivada2 = keyFactoryEC.generatePrivate(clavePrivadaSpec);
        PublicKey clavePublicaEC = keyFactoryEC.generatePublic(x509Spec);







        ECCurve curve = new ECCurve.Fp(
                new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839"), // q
                new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16), // a
                new BigInteger("6b016c3bdcf18941d0d654921475ca71a9db2fb27d1d37796185c2942c0a", 16)); // b

        ECParameterSpec spec = new ECParameterSpec(
                curve,
                curve.decodePoint(Hex.decode("020ffa963cdca8816ccc33b8642bedf905c3d358573d3f27fbbd3b3cb9aaaf")), // G
                new BigInteger("883423532389192164791648750360308884807550341691627752275345424702807307")); // n


        ECPrivateKeySpec privKeySpec = new ECPrivateKeySpec(
                new BigInteger("876300101507107567501066130761671078357010671067781776716671676178726717"), // d
                spec);

        ECPublicKeySpec pubKeySpec = new ECPublicKeySpec(
                curve.decodePoint(Hex.decode("025b6dc53bc61a2548ffb0f671472de6c9521a9d2d2534e65abfcbd5fe0c70")), // Q
                spec);

        //
        // set up the keys
        //
        PrivateKey privKey;
        PublicKey pubKey;

        try
        {
            KeyFactory fact = KeyFactory.getInstance("ECDSA", "BC");

            privKey = fact.generatePrivate(privKeySpec);
            pubKey = fact.generatePublic(pubKeySpec);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
