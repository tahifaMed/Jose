package com.natixis.jose.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.natixis.jose.model.ObjectCertificate;

public class CertificateService {

    private static Logger log = LoggerFactory.getLogger(CertificateService.class);

    private CertificateService() {

    }

    public static ObjectCertificate buildObjectCertificate(String keyStoreFilePath, String password, String alias) {
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
            log.debug("CertificateService.buildObjectCertificate CertificateEncodingException {}", e);
        } catch (KeyStoreException e) {
            log.debug("CertificateService.buildObjectCertificate KeyStoreException {}", e);
        } catch (FileNotFoundException e) {
            log.debug("CertificateService.buildObjectCertificate FileNotFoundException {}", e);
        } catch (NoSuchAlgorithmException e) {
            log.debug("CertificateService.buildObjectCertificate NoSuchAlgorithmException {}", e);
        } catch (IOException e) {
            log.debug("ObjectEncryptService.addHeader IOException {}", e);
        } catch (UnrecoverableKeyException e) {
            log.debug("ObjectEncryptService.addHeader UnrecoverableKeyException {}", e);
        }

        return objectCertificate;
    }

}
