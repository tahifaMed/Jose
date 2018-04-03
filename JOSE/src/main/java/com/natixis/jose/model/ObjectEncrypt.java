package com.natixis.jose.model;

import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;

public class ObjectEncrypt {

    private JWEEncrypter encrypter;

    private JWEHeader header;

    private ObjectCertificate objectCertificate;

    private JWEObject jweObject;

    private String data;

    public JWEEncrypter getEncrypter() {
        return encrypter;
    }

    public void setEncrypter(JWEEncrypter encrypter) {
        this.encrypter = encrypter;
    }

    public JWEHeader getHeader() {
        return header;
    }

    public void setHeader(JWEHeader header) {
        this.header = header;
    }

    public JWEObject getJweObject() {
        return jweObject;
    }

    public void setJweObject(JWEObject jweObject) {
        this.jweObject = jweObject;
    }

    /**
     * @return the objectCertificate
     */
    public ObjectCertificate getObjectCertificate() {
        return objectCertificate;
    }

    /**
     * @param objectCertificate
     *            the objectCertificate to set
     */
    public void setObjectCertificate(ObjectCertificate objectCertificate) {
        this.objectCertificate = objectCertificate;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

}
