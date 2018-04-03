package com.natixis.jose.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.springframework.stereotype.Service;

import com.natixis.jose.model.ObjectCertificate;

@Service
public class CertificateService {

    public static ObjectCertificate buildObjectCertificate(String keyStoreFilePath, String password, String alias)
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, IOException {
        ObjectCertificate objectCertificate = new ObjectCertificate();
        try {
            objectCertificate.setAlias(alias);
            objectCertificate.setKeyStore(KeyStore.getInstance("JKS"));
            char[] keyStorePassword = password.toCharArray();
            InputStream keyStoreData = new FileInputStream(keyStoreFilePath);
            objectCertificate.getKeyStore().load(keyStoreData, keyStorePassword);
            objectCertificate.setPrivateKey((PrivateKey) objectCertificate.getKeyStore()
                    .getKey(objectCertificate.getAlias(), keyStorePassword));

            objectCertificate.setCertificate(
                    objectCertificate.getKeyStore().getCertificateChain(objectCertificate.getAlias())[0]);
            objectCertificate.setPublicKey(objectCertificate.getCertificate().getPublicKey());
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        return objectCertificate;
    }

}
