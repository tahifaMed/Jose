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
import com.natixis.jose.model.ObjectSign;
import com.natixis.jose.service.ObjectSignService;
import com.nimbusds.jose.JOSEException;

import net.minidev.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JoseApplicationConfiguration.class })
public class ObjectSignUnitTest {

    private Logger log = LoggerFactory.getLogger(ObjectSignUnitTest.class);

    @Autowired
    private ObjectSignService objectSignService;

    private ObjectSign objectSign;

    String keyFilePath = "C:/work/dev/workspaces/dizzip3/p2pStore.jks";
    String password = "azerty";
    String alias = "p2pKeyStore";

    @Before
    public void init() {
        objectSign = objectSignService.buildObjectSign(keyFilePath, password, alias);
        objectSignService.addHeader(objectSign, "JOSE+JSON", true);

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
    }

    @Test
    public void test_Sign_compact_Object() throws JOSEException {

        String compactSignature = objectSignService.sign(objectSign, false);
        log.info("compactSignature :{}", compactSignature);
        String verifiedCompactSignature = objectSignService.verifySignedObject(keyFilePath, password, alias,
                compactSignature, false);
        log.info("verifiedCompactSignature : {}", verifiedCompactSignature);
        assertEquals(objectSign.getData(), verifiedCompactSignature);
    }

    @Test
    public void test_Sign_flattened_Object() throws JOSEException {

        String flattenedSignature = objectSignService.sign(objectSign, true);
        log.info("flattenedSignature :{}", flattenedSignature);
        String verifiedFlatSignature = objectSignService.verifySignedObject(keyFilePath, password, alias,
                flattenedSignature, true);
        log.info("verifiedCompactSignature : {}", verifiedFlatSignature);
        assertEquals(objectSign.getData(), verifiedFlatSignature);

    }

}
