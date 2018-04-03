package com.natixis.jose.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSAEncrypter;

import net.minidev.json.JSONObject;

public class JWEEncrypt2 {

    public static void main(String[] args) throws JOSEException, NoSuchAlgorithmException, ParseException,
            KeyStoreException, CertificateException, IOException, UnrecoverableKeyException {

        KeyStore keyStore = KeyStore.getInstance("JKS");
        char[] keyStorePassword = "azerty".toCharArray();
        InputStream keyStoreData = new FileInputStream("C:/work/dev/workspaces/dizzip3/p2pStore.jks");
        keyStore.load(keyStoreData, keyStorePassword);

        Certificate cert = keyStore.getCertificateChain("p2pKeyStore")[0];
        PublicKey publicKey = cert.getPublicKey();

        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);

        // Set the plain text

        JSONObject json = new JSONObject();
        json.put("lhlkh", "mjmljmlj");
        json.put("oioiyoiy", "oiyoiyoiy");
        json.put("mmioiyoiy", "oiyoiyoiy");
        json.put("iioiyoiy", "oiyoiyoiy");
        json.put("zioiyoiy", "oiyoiyoiy");
        json.put("aioiyoiy", "oiyoiyoiy");
        json.put("ioiyoiy", "oiyoiyoiy");
        json.put("sioiyoiy", "oiyoiyoiy");
        json.put("xioiyoiy", "oiyoiyoiy");
        json.put("cioiyoiy", "oiyoiyoiy");
        json.put("vioiyoiy", "oiyoiyoiy");
        json.put("bioiyoiy", "oiyoiyoiy");
        json.put("nioiyoiy", "oiyoiyoiy");
        json.put("tioiyoiy", "oiyoiyoiy");
        json.put("rioiyoiy", "oiyoiyoiy");
        json.put("eioiyoiy", "oiyoiyoiy");
        Payload payload = new Payload("Hello world!");

        JWEEncrypter encrypter = new RSAEncrypter((RSAPublicKey) publicKey);

        // Create the JWE object and encrypt it
        JWEObject jweObject = new JWEObject(header, payload);
        jweObject.encrypt(encrypter);

        // Serialise to compact JOSE form...
        String jweString = jweObject.serialize();
        System.out.println(jweString);
    }

}
