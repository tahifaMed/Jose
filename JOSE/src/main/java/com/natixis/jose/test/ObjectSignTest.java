package com.natixis.jose.test;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import com.natixis.jose.model.ObjectSign;
import com.natixis.jose.service.ObjectSignService;
import com.nimbusds.jose.JOSEException;

import net.minidev.json.JSONObject;

public class ObjectSignTest {

    private ObjectSignService objectSignService = new ObjectSignService();

    public ObjectSignTest() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, IOException {
        ObjectSign objectSign = objectSignService.buildObjectSign("C:/work/dev/workspaces/dizzip3/p2pStore.jks",
                "azerty", "p2pKeyStore");
        objectSignService.addHeader(objectSign, "JOSE+JSON", true);

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
            objectSign.setData(json.toJSONString());
            System.out.println(objectSignService.sign(objectSign));
            System.out.println(objectSignService.signFlattenedJWS(objectSign));
        } catch (JOSEException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new ObjectSignTest();
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
