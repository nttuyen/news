package com.nttuyen.common.util;

import org.json.JSONObject;

/**
 * @author nttuyen266@gmail.com
 */
public class JSONUtils {
    public static <T> T get(JSONObject json, String key) {
        if(key == null) {
            return null;
        }
        String[] keys = key.split("/");
        int length = keys.length;
        JSONObject j = json;
        T val = null;
        for(int i = 0; i < length; i++) {
            if(i == length - 1) {
                val = (T)j.get(keys[i]);
                break;
            } else {
                j = j.getJSONObject(keys[i]);
            }
        }

        return val;
    }
}
