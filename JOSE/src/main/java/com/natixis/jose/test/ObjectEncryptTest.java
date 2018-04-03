package com.natixis.jose.test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import com.natixis.jose.model.ObjectEncrypt;
import com.natixis.jose.service.ObjectEncryptService;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;

import net.minidev.json.JSONObject;

public class ObjectEncryptTest {

    private ObjectEncryptService objectEncryptService = new ObjectEncryptService();

    public ObjectEncryptTest() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, IOException {
        ObjectEncrypt objectEncrypt = objectEncryptService
                .buildObjectEncrypt("C:/work/dev/workspaces/dizzip3/p2pStore.jks", "azerty", "p2pKeyStore");
        objectEncryptService.addHeader(objectEncrypt, JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM, "JOSE+JSON",
                true);

        try {
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
            objectEncrypt.setData(json.toJSONString());
            System.out.println(objectEncryptService.encrypt(objectEncrypt));
            System.out.println(objectEncryptService.encryptFlattenedJWE(objectEncrypt));
        } catch (JOSEException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new ObjectEncryptTest();
        } catch (UnrecoverableKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
