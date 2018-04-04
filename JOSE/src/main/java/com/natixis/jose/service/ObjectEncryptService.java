package com.natixis.jose.service;

import java.security.cert.CertificateEncodingException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.natixis.jose.model.ObjectCertificate;
import com.natixis.jose.model.ObjectEncrypt;
import com.natixis.jose.util.SignUtil;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.util.Base64URL;

@Service
public class ObjectEncryptService {

    private Logger log = LoggerFactory.getLogger(ObjectEncryptService.class);

    public ObjectEncrypt buildObjectEncrypt(String keyStoreFilePath, String password, String alias) {
        ObjectEncrypt objectEncrypt = new ObjectEncrypt();
        objectEncrypt
                .setObjectCertificate(CertificateService.buildObjectCertificate(keyStoreFilePath, password, alias));

        objectEncrypt
                .setEncrypter(new RSAEncrypter((RSAPublicKey) objectEncrypt.getObjectCertificate().getPublicKey()));

        return objectEncrypt;
    }

    public void addHeader(ObjectEncrypt objectEncrypt, JWEAlgorithm algo, EncryptionMethod encryptionMethod, String typ,
            boolean x5t) {

        try {
            String x5tString;
            JWEHeader jWEHeader;
            if (x5t) {
                x5tString = DigestUtils.sha1Hex(objectEncrypt.getObjectCertificate().getCertificate().getEncoded());
                jWEHeader = new JWEHeader.Builder(algo, encryptionMethod).type(new JOSEObjectType(typ))
                        .x509CertSHA256Thumbprint(new Base64URL(x5tString)).build();
            } else {
                jWEHeader = new JWEHeader.Builder(algo, encryptionMethod).type(new JOSEObjectType(typ)).build();
            }
            objectEncrypt.setHeader(jWEHeader);
        } catch (CertificateEncodingException e) {
            log.debug("ObjectEncryptService.addHeader CertificateEncodingException {}", e);
        }
    }

    public String encrypt(ObjectEncrypt objectEncrypt, boolean flat) throws JOSEException {
        objectEncrypt.setJweObject(new JWEObject(objectEncrypt.getHeader(), new Payload(objectEncrypt.getData())));
        objectEncrypt.getJweObject().encrypt(objectEncrypt.getEncrypter());
        if (!flat)
            return objectEncrypt.getJweObject().serialize();
        return SignUtil.fromCompactToFlattenedEncrypt(objectEncrypt.getJweObject().serialize());
    }

    public String verifyEncryptObject(String keyStoreFilePath, String password, String alias, String encryption,
            boolean flat) {
        ObjectCertificate certificate = CertificateService.buildObjectCertificate(keyStoreFilePath, password, alias);
        JWEObject jweObject = null;
        try {
            if (flat)
                jweObject = JWEObject.parse(SignUtil.fromFlattenedToCompactEncrypt(encryption));
            else
                jweObject = JWEObject.parse(encryption);

            RSADecrypter decrypter = new RSADecrypter(certificate.getPrivateKey());

            jweObject.decrypt(decrypter);
            return jweObject.getPayload().toString();
        } catch (JOSEException e) {
            log.debug("ObjectSignService.verifySignedObject JOSEException {}", e);
        } catch (ParseException e) {
            log.debug("ObjectSignService.verifySignedObject ParseException {}", e);
        }
        return null;
    }

}
