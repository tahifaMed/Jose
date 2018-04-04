package com.natixis.jose.util;

import com.natixis.jose.model.FlattenedEncrypt;
import com.natixis.jose.model.FlattenedSignature;

public class SignUtil {

    public static String fromCompactToFlattenedSignature(String signature) {
        String[] splitted = signature.split("\\.");
        FlattenedSignature flattenedSignature = new FlattenedSignature();
        if (splitted.length > 0 && splitted.length == 3) {

            flattenedSignature.setPayload(splitted[0]);
            flattenedSignature.setProtectede(splitted[1]);
            flattenedSignature.setSignature(splitted[2]);
        }
        return JsonUtil.objectToJson(flattenedSignature);
    }

    public static String fromFlattenedToCompactSignature(String signature) {
        FlattenedSignature flattenedSignature = (FlattenedSignature) JsonUtil.jsonToObject(signature,
                FlattenedSignature.class);
        return flattenedSignature.getPayload() + "." + flattenedSignature.getProtectede() + "."
                + flattenedSignature.getSignature();
    }

    public static String fromCompactToFlattenedEncrypt(String encrypt) {
        String[] splitted = encrypt.split("\\.");
        FlattenedEncrypt flattenedEncrypt = new FlattenedEncrypt();
        if (splitted.length > 0 && splitted.length == 5) {

            flattenedEncrypt.setProtectede(splitted[0]);
            flattenedEncrypt.setEncryptedKey(splitted[1]);
            flattenedEncrypt.setIv(splitted[2]);
            flattenedEncrypt.setCiphertext(splitted[3]);
            flattenedEncrypt.setTag(splitted[4]);
        }
        return JsonUtil.objectToJson(flattenedEncrypt);
    }

    public static String fromFlattenedToCompactEncrypt(String encrypt) {
        FlattenedEncrypt flattenedEncrypt = (FlattenedEncrypt) JsonUtil.jsonToObject(encrypt, FlattenedEncrypt.class);
        return flattenedEncrypt.getProtectede() + "." + flattenedEncrypt.getEncryptedKey() + "."
                + flattenedEncrypt.getIv() + "." + flattenedEncrypt.getCiphertext() + "." + flattenedEncrypt.getTag();
    }

}
