package com.natixis.jose.service;

import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.natixis.jose.model.ObjectEncrypt;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.util.Base64URL;

import net.minidev.json.JSONObject;

@Service
public class ObjectEncryptService implements Serializable {

    private static final long serialVersionUID = 9120282007234900763L;

    public ObjectEncrypt buildObjectEncrypt(String keyStoreFilePath, String password, String alias)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
            UnrecoverableKeyException {
        ObjectEncrypt objectEncrypt = new ObjectEncrypt();
        objectEncrypt
                .setObjectCertificate(CertificateService.buildObjectCertificate(keyStoreFilePath, password, alias));

        objectEncrypt
                .setEncrypter(new RSAEncrypter((RSAPublicKey) objectEncrypt.getObjectCertificate().getPublicKey()));
        return objectEncrypt;
    }

    public void addHeader(ObjectEncrypt objectEncrypt, JWEAlgorithm algo, EncryptionMethod encryptionMethod, String typ,
            boolean x5t) throws CertificateEncodingException {

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
    }

    public String encrypt(ObjectEncrypt objectEncrypt) throws JOSEException {
        objectEncrypt.setJweObject(new JWEObject(objectEncrypt.getHeader(), new Payload(objectEncrypt.getData())));
        objectEncrypt.getJweObject().encrypt(objectEncrypt.getEncrypter());
        return objectEncrypt.getJweObject().serialize();
    }

    public String encryptFlattenedJWE(ObjectEncrypt objectEncrypt) throws JOSEException {
        objectEncrypt.setJweObject(new JWEObject(objectEncrypt.getHeader(), new Payload(objectEncrypt.getData())));
        objectEncrypt.getJweObject().encrypt(objectEncrypt.getEncrypter());
        String s = objectEncrypt.getJweObject().serialize();
        String[] splitted = s.split("\\.");
        JSONObject json = new JSONObject();
        if (splitted.length > 0) {

            json.put("protected", splitted[0]);
            json.put("encrypted_key", splitted[1]);
            json.put("iv", splitted[2]);
            json.put("ciphertext", splitted[3]);
            json.put("tag", splitted[4]);
        }
        return json.toJSONString();
    }

}
