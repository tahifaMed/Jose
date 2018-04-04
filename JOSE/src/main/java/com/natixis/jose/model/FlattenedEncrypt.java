package com.natixis.jose.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlattenedEncrypt {

    @JsonProperty("encrypted_key")
    private String encryptedKey;

    @JsonProperty("protected")
    private String protectede;

    private String iv;

    private String ciphertext;

    private String tag;

    /**
     * @return the encryptedKey
     */
    public String getEncryptedKey() {
        return encryptedKey;
    }

    /**
     * @param encryptedKey
     *            the encryptedKey to set
     */
    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
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
     * @return the iv
     */
    public String getIv() {
        return iv;
    }

    /**
     * @param iv
     *            the iv to set
     */
    public void setIv(String iv) {
        this.iv = iv;
    }

    /**
     * @return the ciphertext
     */
    public String getCiphertext() {
        return ciphertext;
    }

    /**
     * @param ciphertext
     *            the ciphertext to set
     */
    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     *            the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

}
