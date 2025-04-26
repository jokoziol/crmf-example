package dev.jokoziol.crmf.main;

import dev.jokoziol.crmf.CertReqMsg;
import org.bouncycastle.asn1.crmf.CertReqMessages;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.util.Base64;

public class CrmfMain {

    public static void main(String[] args) throws Exception {

        // Add BouncyCastleProvider
        Security.addProvider(new BouncyCastleProvider());

        // Generate Key Pair
        final KeyPair tlsKeyPair = generateKeyPair();
        final KeyPair signingKeyPair = generateKeyPair();

        // Build Extensions
        final Extensions tlsExtensions = getTlsExtensions();
        final Extensions signingExtensions = getSigningExtensions();

        // Build CRMF
        final X500Name tlsSubject = new X500Name("CN=exampleTls");
        final X500Name signingSubject = new X500Name("CN=exampleSigning");

        final CertReqMsg certReqMsgTls = new CertReqMsg(1, tlsSubject, tlsKeyPair).setExtensions(tlsExtensions);
        final CertReqMsg certReqMsgSigning = new CertReqMsg(2, signingSubject, signingKeyPair).setExtensions(signingExtensions);

        final CertReqMessages certReqMessages = new CertReqMessages(new org.bouncycastle.asn1.crmf.CertReqMsg[]{
                certReqMsgTls.build(),
                certReqMsgSigning.build()
        });

        final byte[] crmfBytes = certReqMessages.getEncoded();

        System.out.println("Asn1 Dump:");
        System.out.println(ASN1Dump.dumpAsString(certReqMessages));

        System.out.println("Crmf Base64:");
        System.out.println("\t" + Base64.getEncoder().encodeToString(crmfBytes));
    }

    private static Extensions getTlsExtensions() throws IOException {
        final ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
        extensionsGenerator.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyAgreement | KeyUsage.keyEncipherment));
        extensionsGenerator.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(new KeyPurposeId[]{
                KeyPurposeId.id_kp_serverAuth,
                KeyPurposeId.id_kp_clientAuth
        }));

        return extensionsGenerator.generate();
    }

    private static Extensions getSigningExtensions() throws IOException {
        final ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
        extensionsGenerator.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature));

        return extensionsGenerator.generate();
    }

    private static KeyPair generateKeyPair() throws Exception{
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        keyPairGenerator.initialize(256);

        return keyPairGenerator.generateKeyPair();
    }
}
