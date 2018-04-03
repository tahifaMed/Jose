package com.natixis.jose.service;

import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.natixis.jose.model.ObjectSign;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.util.Base64URL;

import net.minidev.json.JSONObject;

@Service
public class ObjectSignService implements Serializable {

    private static final long serialVersionUID = 9120282007234900763L;

    public ObjectSign buildObjectSign(String keyStoreFilePath, String password, String alias) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
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
            e.printStackTrace();
        }

    }

    public String sign(ObjectSign objectSign) throws JOSEException {
        objectSign.setJwsObject(new JWSObject(objectSign.getHeader(), new Payload(objectSign.getData())));
        objectSign.getJwsObject().sign(objectSign.getSigner());
        return objectSign.getJwsObject().serialize();
    }

    public String signFlattenedJWS(ObjectSign objectSign) throws JOSEException {
        objectSign.setJwsObject(new JWSObject(objectSign.getHeader(), new Payload(objectSign.getData())));
        objectSign.getJwsObject().sign(objectSign.getSigner());
        String s = objectSign.getJwsObject().serialize();
        String[] splitted = s.split("\\.");
        JSONObject json = new JSONObject();
        if (splitted.length > 0) {

            json.put("payload", splitted[0]);
            json.put("protected", splitted[1]);
            json.put("signature", splitted[2]);
        }
        return json.toJSONString();
    }

}
