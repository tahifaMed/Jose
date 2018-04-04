package com.natixis.jose.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlattenedSignature {

    private String payload;
    @JsonProperty("protected")
    private String protectede;
    private String signature;

    /**
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * @param payload
     *            the payload to set
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * @return the protectede
     */
    public String getProtectede() {
        return protectede;
    }

    /**
     * @param protectede
     *            the protectede to set
     */
    public void setProtectede(String protectede) {
        this.protectede = protectede;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature
     *            the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

}
