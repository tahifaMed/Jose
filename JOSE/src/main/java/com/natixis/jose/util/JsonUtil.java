package com.natixis.jose.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author tahifamo
 *
 */
public class JsonUtil {

    private static Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }

    /**
     * @param object
     * @return String
     */
    public static <T> String objectToJson(T object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.debug("objectToJson error : ", e);
        }
        return null;
    }

    /**
     * @param json
     * @param classe
     * @return Object
     */
    public static Object jsonToObject(String json, Class<?> classe) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, classe);
        } catch (IOException e) {
            log.debug("jsonToObject error : ", e);
        }
        return null;
    }

}
