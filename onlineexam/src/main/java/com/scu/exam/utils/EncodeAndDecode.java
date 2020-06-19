package com.scu.exam.utils;

import org.junit.Test;

import java.security.MessageDigest;

public class EncodeAndDecode {

    /***
     * SHA加密 生成40位SHA码
     */
    public static String shaEncode(String data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA");

        byte[] byteArray = data.getBytes("UTF-8");

        // md5Bytes的长度为20
        byte[] md5Bytes = sha.digest(byteArray);

        // 转换成16进制字符串
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;

            // 为了满足40位长度，当值小于16时需要先添加一位0(小于16的话用一位就能表示)
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    @Test
    public void generateSecret() throws Exception {
        String str = new String("123456");
        System.out.println("原始：" + str);
        System.out.println("SHA后：" + EncodeAndDecode.shaEncode(str));
    }

}
