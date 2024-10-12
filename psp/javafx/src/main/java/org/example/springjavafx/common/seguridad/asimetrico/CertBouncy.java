package org.example.springjavafx.common.seguridad.asimetrico;

import com.google.common.io.Files;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CertBouncy {

    public static void main(String[] args) throws CertificateException, InvalidKeyException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, IOException, InvalidKeySpecException {
        X509Certificate selfSignedX509Certificate = new CertBouncy().generateSelfSignedX509Certificate();

        Files.write(selfSignedX509Certificate.getEncoded(), new File("auto.crt"));
        System.out.println(selfSignedX509Certificate);



        PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(Files.asByteSource(new File("dam1024.privada")).read());
        KeyFactory keyFactoryRSA = KeyFactory.getInstance("RSA");
        PrivateKey clavePrivadaCA = keyFactoryRSA.generatePrivate(clavePrivadaSpec);

        X509Certificate signedX509Certificate = new CertBouncy().generateSelfSignedX509Certificate(clavePrivadaCA);

        Files.write(signedX509Certificate.getEncoded(), new File("caSign.crt"));

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert2 = (X509Certificate)cf.generateCertificate(new FileInputStream("caSign.crt"));

//        X509Certificate certDeDisco =  X509CertUtils.parse(Files.asByteSource(new File("caSign.crt")).read());

        X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(Files.asByteSource(new File("dam1024.publica")).read());
        PublicKey clavePublicaCA = keyFactoryRSA.generatePublic(clavePublicaSpec);

        cert2.verify(clavePublicaCA);
        System.out.println(cert2);


    }
    protected PrivateKey signingKey;
    protected Certificate signingCertificate;
    protected List<Certificate> certificateChain;
    protected String serialNumberOrKeyId;
    protected boolean closedSystemSignatureDevice;
    public void intialise() {
        try {//from  www.  ja  v a2s  .c  o  m
            //create random demonstration ECC keys
            final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
            kpg.initialize(256); //256 bit ECDSA key

            //create a key pair for the demo Certificate Authority
            final KeyPair caKeyPair = kpg.generateKeyPair();

            //create a key pair for the signature certificate, which is going to be used to sign the receipts
            final KeyPair signingKeyPair = kpg.generateKeyPair();

            //get references to private keys for the CA and the signing key
            final PrivateKey caKey = caKeyPair.getPrivate();
            signingKey = signingKeyPair.getPrivate();

            //create CA certificate and add it to the certificate chain
            //NOTE: DO NEVER EVER USE IN A REAL CASHBOX, THIS IS JUST FOR DEMONSTRATION PURPOSES
            //NOTE: these certificates have random values, just for the demonstration purposes here
            //However, for testing purposes the most important feature is the EC256 Signing Key, since this is required
            //by the RK Suite
            final X509v3CertificateBuilder caBuilder = new X509v3CertificateBuilder(new X500Name("CN=RegKassa ZDA"),
                    BigInteger.valueOf(new SecureRandom().nextLong()), new Date(System.currentTimeMillis() - 10000),
                    new Date(System.currentTimeMillis() + 24L * 3600 * 1000), new X500Name("CN=RegKassa CA"),
                    SubjectPublicKeyInfo.getInstance(caKeyPair.getPublic().getEncoded()));
            caBuilder.addExtension(X509Extension.basicConstraints, true, new BasicConstraints(false));
            caBuilder.addExtension(X509Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature));
            final X509CertificateHolder caHolder = caBuilder
                    .build(new JcaContentSignerBuilder("SHA256withECDSA").setProvider("BC").build(caKey));
            final X509Certificate caCertificate = new JcaX509CertificateConverter().setProvider("BC")
                    .getCertificate(caHolder);
            certificateChain = new ArrayList<Certificate>();
            certificateChain.add(caCertificate);

            //create signing cert
            final long serialNumberCertificate = new SecureRandom().nextLong();
            if (!closedSystemSignatureDevice) {
                serialNumberOrKeyId = Long.toHexString(serialNumberCertificate);
            }

            final X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                    new X500Name("CN=RegKassa CA"), BigInteger.valueOf(Math.abs(serialNumberCertificate)),
                    new Date(System.currentTimeMillis() - 10000),
                    new Date(System.currentTimeMillis() + 24L * 3600 * 1000),
                    new X500Name("CN=Signing certificate"),
                    SubjectPublicKeyInfo.getInstance(signingKeyPair.getPublic().getEncoded()));
//            certBuilder.addExtension(X509Extension.basicConstraints, true, new BasicConstraints(false));
//            certBuilder.addExtension(X509Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature));
            final X509CertificateHolder certHolder = certBuilder
                    .build(new JcaContentSignerBuilder("SHA256withECDSA").setProvider("BC").build(caKey));
            signingCertificate = new JcaX509CertificateConverter().setProvider("BC").getCertificate(certHolder);

        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (final OperatorCreationException e) {
            e.printStackTrace();
        } catch (final CertIOException e) {
            e.printStackTrace();
        } catch (final CertificateException e) {
            e.printStackTrace();
        }
    }


    public X509Certificate generateSelfSignedX509Certificate() throws CertificateException, InvalidKeyException, IllegalStateException,
            NoSuchProviderException, NoSuchAlgorithmException, SignatureException {
        addBouncyCastleAsSecurityProvider();
        // generate a key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(4096, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // build a certificate generator
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

        X500Principal dnName = new X500Principal("cn=example");
        X500Principal dnSubject = new X500Principal("cn=example");

        // add some options
        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setSubjectDN(dnSubject);
        certGen.setIssuerDN(dnName); // use the same
        // yesterday
        certGen.setNotBefore(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
        // in 2 years
        certGen.setNotAfter(Date.from(LocalDate.now().plus(1, ChronoUnit.YEARS).atStartOfDay().toInstant(ZoneOffset.UTC)));
        certGen.setPublicKey(keyPair.getPublic());
        certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
//        certGen.addExtension(X509Extensions.ExtendedKeyUsage, true,
//                new ExtendedKeyUsage(KeyPurposeId.id_kp_timeStamping));

        // finally, sign the certificate with the private key of the same KeyPair
        X509Certificate cert = certGen.generate(keyPair.getPrivate());

        cert.verify(keyPair.getPublic());
        return cert;
    }

    public X509Certificate generateSelfSignedX509Certificate(PrivateKey signCertificateKey) throws CertificateException, InvalidKeyException, IllegalStateException,
            NoSuchProviderException, NoSuchAlgorithmException, SignatureException {
        addBouncyCastleAsSecurityProvider();
        // generate a key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(4096, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // build a certificate generator
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        X500Principal dnName = new X500Principal("cn=example");
        X500Principal dnSubject = new X500Principal("cn=example");

        // add some options
        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setSubjectDN(dnSubject);
        certGen.setIssuerDN(new X509Principal("CN=tuputamadre")); // use the same
        // yesterday
        certGen.setNotBefore(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
        // in 2 years
        certGen.setNotAfter(Date.from(LocalDate.now().plus(365, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC)));
        certGen.setPublicKey(keyPair.getPublic());
        certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
//        certGen.addExtension(X509Extensions.ExtendedKeyUsage, true,
//                new ExtendedKeyUsage(KeyPurposeId.id_kp_timeStamping));

        // finally, sign the certificate with the private key of the same KeyPair
        X509Certificate cert = certGen.generate(signCertificateKey);


        //cert.verify
        return cert;
    }

    public void addBouncyCastleAsSecurityProvider() {
        System.out.println(Security.addProvider(new BouncyCastleProvider()));
    }
}
