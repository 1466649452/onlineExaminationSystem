package com.scu.exam.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Iterator;

public class JsonOperation {

    /**
     * 合并两个JSONObject
     */
    public static JSONObject combineJson(JSONObject srcJObjStr, JSONObject addJObjStr) throws JSONException {
        if (addJObjStr == null || addJObjStr.isEmpty()) {
            return srcJObjStr;
        }
        if (srcJObjStr == null || srcJObjStr.isEmpty()) {
            return addJObjStr;
        }
        for (String key: addJObjStr.keySet()) {
            Object value = addJObjStr.get(key);
            if (!srcJObjStr.containsKey(key)) {
                // src中没有
                srcJObjStr.put(key, value);
            } else {
                // src中有
                continue;
            }
        }
        return srcJObjStr;
    }

    @Test
    public void tseis(){
        JSONObject j1=new JSONObject();
        JSONObject j2=new JSONObject();

        j1.put("id",1);
        j1.put("name","yan");
        j2.put("id",1);
        j2.put("address","shang");

        System.out.println(JsonOperation.combineJson(j1,j2));
    }

}
