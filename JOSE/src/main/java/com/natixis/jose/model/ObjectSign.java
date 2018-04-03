package com.natixis.jose.model;

import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;

public class ObjectSign {

    private JWSSigner signer;

    private JWSHeader header;

    private ObjectCertificate objectCertificate;

    private JWSObject jwsObject;

    private String data;

    public JWSSigner getSigner() {
        return signer;
    }

    public void setSigner(JWSSigner signer) {
        this.signer = signer;
    }

    public JWSHeader getHeader() {
        return header;
    }

    public void setHeader(JWSHeader header) {
        this.header = header;
    }

    public JWSObject getJwsObject() {
        return jwsObject;
    }

    public void setJwsObject(JWSObject jwsObject) {
        this.jwsObject = jwsObject;
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
