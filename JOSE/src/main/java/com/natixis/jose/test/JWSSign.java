package com.natixis.jose.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;

import org.apache.commons.codec.digest.DigestUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.util.Base64URL;

import net.minidev.json.JSONObject;

public class JWSSign {

    public static void main(String[] args) throws JOSEException, NoSuchAlgorithmException, ParseException,
            KeyStoreException, CertificateException, IOException, UnrecoverableKeyException {

        KeyStore keyStore = KeyStore.getInstance("JKS");
        char[] keyStorePassword = "azerty".toCharArray();
        InputStream keyStoreData = new FileInputStream("C:/work/dev/workspaces/dizzip3/p2pStore.jks");
        keyStore.load(keyStoreData, keyStorePassword);
        keyStore.containsAlias("p2pKeyStore");
        Certificate cert = keyStore.getCertificateChain("p2pKeyStore")[0];

        String x5t = DigestUtils.sha1Hex(cert.getEncoded());

        PrivateKey privateKey = (PrivateKey) keyStore.getKey("p2pKeyStore", keyStorePassword);

        // Create RSA-signer with the private key
        JWSSigner signer = new RSASSASigner((RSAPrivateKey) privateKey);

        // Prepare JWS object with simple string as payload

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).type(new JOSEObjectType("JOSE+JSON"))
                .x509CertSHA256Thumbprint(new Base64URL(x5t)).build();
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
        JWSObject jwsObject = new JWSObject(header, new Payload(json.toJSONString()));

        // Compute the RSA signature
        jwsObject.sign(signer);
        String s = jwsObject.serialize();
        System.out.println(s);
        String[] splitter = s.split("\\.");
        System.out.println(splitter[0]);
    }

}
