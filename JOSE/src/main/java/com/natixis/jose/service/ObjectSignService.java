package com.natixis.jose.service;

import java.security.cert.CertificateEncodingException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.natixis.jose.model.ObjectCertificate;
import com.natixis.jose.model.ObjectSign;
import com.natixis.jose.util.SignUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.util.Base64URL;

@Service
public class ObjectSignService {

    private Logger log = LoggerFactory.getLogger(ObjectSignService.class);

    public ObjectSign buildObjectSign(String keyStoreFilePath, String password, String alias) {
        ObjectSign objectSign = new ObjectSign();
        objectSign.setObjectCertificate(CertificateService.buildObjectCertificate(keyStoreFilePath, password, alias));

        objectSign.setSigner(new RSASSASigner(objectSign.getObjectCertificate().getPrivateKey()));

        return objectSign;
    }

    public void addHeader(ObjectSign objectSign, String typ, boolean x5t) {
        try {

            String x5tString;
            if (x5t) {
                x5tString = DigestUtils.sha1Hex(objectSign.getObjectCertificate().getCertificate().getEncoded());
                objectSign.setHeader(new JWSHeader.Builder(JWSAlgorithm.RS256).type(new JOSEObjectType(typ))
                        .x509CertSHA256Thumbprint(new Base64URL(x5tString)).build());
            } else {
                objectSign.setHeader(new JWSHeader.Builder(JWSAlgorithm.RS256).type(new JOSEObjectType(typ)).build());
            }

        } catch (CertificateEncodingException e) {
            log.debug("ObjectSignService.addHeader CertificateEncodingException {}", e);
        }

    }

    /**
     * sign an object
     * 
     * @param objectSign
     * @param flat
     *            generate a flattened version
     * @return String signature
     * @throws JOSEException
     */
    public String sign(ObjectSign objectSign, boolean flat) throws JOSEException {
        objectSign.setJwsObject(new JWSObject(objectSign.getHeader(), new Payload(objectSign.getData())));
        objectSign.getJwsObject().sign(objectSign.getSigner());
        if (!flat)
            return objectSign.getJwsObject().serialize();
        return SignUtil.fromCompactToFlattenedSignature(objectSign.getJwsObject().serialize());
    }

    /**
     * 
     * @param objectSign
     * @param signature
     * @param flat
     * @return String payload
     */
    public String verifySignedObject(String keyStoreFilePath, String password, String alias, String signature,
            boolean flat) {
        ObjectCertificate objectCertificate = CertificateService.buildObjectCertificate(keyStoreFilePath, password,
                alias);
        JWSObject jwsObject = null;
        try {
            if (flat)
                jwsObject = JWSObject.parse(SignUtil.fromFlattenedToCompactSignature(signature));
            else
                jwsObject = JWSObject.parse(signature);

            JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) objectCertificate.getPublicKey());

            jwsObject.verify(verifier);
            return jwsObject.getPayload().toString();
        } catch (JOSEException e) {
            log.debug("ObjectSignService.verifySignedObject JOSEException {}", e);
        } catch (ParseException e) {
            log.debug("ObjectSignService.verifySignedObject ParseException {}", e);
        }
        return null;
    }

}
