package com.natixis.jose.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.natixis.jose.conf.JoseApplicationConfiguration;
import com.natixis.jose.model.ObjectEncrypt;
import com.natixis.jose.service.ObjectEncryptService;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;

import net.minidev.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JoseApplicationConfiguration.class })
public class ObjectEncryptUnitTest {

    private Logger log = LoggerFactory.getLogger(ObjectEncryptUnitTest.class);

    @Autowired
    private ObjectEncryptService objectEncryptService;

    private ObjectEncrypt objectEncrypt;

    String keyFilePath = "C:/work/dev/workspaces/dizzip3/p2pStore.jks";
    String password = "azerty";
    String alias = "p2pKeyStore";

    @Before
    public void init() {
        objectEncrypt = objectEncryptService.buildObjectEncrypt(keyFilePath, password, alias);
        objectEncryptService.addHeader(objectEncrypt, JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM, "JOSE+JSON",
                true);
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
    }

    @Test
    public void test_Sign_compact_Object() throws JOSEException {

        String compactEncrypt = objectEncryptService.encrypt(objectEncrypt, false);
        log.info("compactEncrypt :{}", compactEncrypt);
        String verifiedCompactEncrypt = objectEncryptService.verifyEncryptObject(keyFilePath, password, alias,
                compactEncrypt, false);
        log.info("verifiedCompactEncrypt : {}", verifiedCompactEncrypt);
        assertEquals(objectEncrypt.getData(), verifiedCompactEncrypt);
    }

    @Test
    public void test_Sign_flattened_Object() throws JOSEException {

        String compactEncrypt = objectEncryptService.encrypt(objectEncrypt, true);
        log.info("compactEncrypt :{}", compactEncrypt);
        String verifiedCompactEncrypt = objectEncryptService.verifyEncryptObject(keyFilePath, password, alias,
                compactEncrypt, true);
        log.info("verifiedCompactEncrypt : {}", verifiedCompactEncrypt);
        assertEquals(objectEncrypt.getData(), verifiedCompactEncrypt);

    }

}
