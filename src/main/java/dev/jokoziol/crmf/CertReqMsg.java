package dev.jokoziol.crmf;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.crmf.CertRequest;
import org.bouncycastle.asn1.crmf.CertTemplateBuilder;
import org.bouncycastle.asn1.crmf.ProofOfPossession;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.crmf.ProofOfPossessionSigningKeyBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.security.KeyPair;

public class CertReqMsg {

    private Extensions extensions = null;

    private final int certId;

    private final X500Name subject;

    private final KeyPair keyPair;

    public CertReqMsg(int certId, X500Name subject, KeyPair keyPair) {
        this.certId = certId;
        this.subject = subject;
        this.keyPair = keyPair;
    }

    public CertReqMsg setExtensions(Extensions extensions){
        this.extensions = extensions;
        return this;
    }

    public org.bouncycastle.asn1.crmf.CertReqMsg build() throws OperatorCreationException {

        final SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
        final CertTemplateBuilder certTemplateBuilder = new CertTemplateBuilder()
                .setPublicKey(subjectPublicKeyInfo)
                .setSubject(subject)
                .setExtensions(extensions);

        final ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withECDSA").build(keyPair.getPrivate());
        final CertRequest certRequest = new CertRequest(new ASN1Integer(certId), certTemplateBuilder.build(), null);

        final ProofOfPossessionSigningKeyBuilder popSkb = new ProofOfPossessionSigningKeyBuilder(certRequest);
        final ProofOfPossession pop = new ProofOfPossession(popSkb.build(contentSigner));

        return new org.bouncycastle.asn1.crmf.CertReqMsg(certRequest, pop, null);
    }
}
