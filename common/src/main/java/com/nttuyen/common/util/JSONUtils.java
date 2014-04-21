package com.nttuyen.common.util;

import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * @author nttuyen266@gmail.com
 */
public class JSONUtils {
    private static final Logger log = Logger.getLogger(JSONUtils.class);
    public static <T> T get(JSONObject json, String key) {
        if(key == null) {
            return null;
        }
        try {
            String[] keys = key.split("\\.");
            int length = keys.length;
            JSONObject j = json;
            T val = null;
            for(int i = 0; i < length; i++) {
                if(!j.has(keys[i])) {
                    return null;
                }
                if(i == length - 1) {
                    val = (T)j.get(keys[i]);
                    break;
                } else {
                    j = j.getJSONObject(keys[i]);
                }
            }

            return val;
        } catch (Exception ex) {
            log.error(ex);
            return null;
        }
    }
}
