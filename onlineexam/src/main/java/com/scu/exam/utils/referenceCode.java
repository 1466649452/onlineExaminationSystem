package com.scu.exam.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scu.exam.pojo.Teacher;
import org.junit.Test;

public class referenceCode {

    /*
    * 序列化与反序列化代码，序列化：对象转字符串
    * */
    @Test
    public void jsontest() throws JsonProcessingException {
        Teacher teacher = new Teacher("12", "34", "56", "78");

        //利用jackson包将对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String jsonstring = mapper.writeValueAsString(teacher);
        System.out.println(jsonstring);

        //禁用未知属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //反序列化为对象
        Teacher temp = mapper.readValue(jsonstring, Teacher.class);

        System.out.println(temp);
        System.out.println(teacher);
    }
}
