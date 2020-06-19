package com.scu.exam.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
* 用于分发token和设定过期时间的工具类
* */
public class TokenSign {

    /*
    * 过期时间一小时，毫秒计算
    * */
    private static final long EXPIRE_TIME=60*1000;

    /*
    * 私钥已加密
    * */
    private static String TOKEN_SECRET="9d4315014d0ed8027cf4e01864998fed8061caa1";

    /**
     * 产生token
     * @param userName
     * @param userId
     * @return
     */
    public static String signToken(String userId,String userName){

        try{
            //设置失效时间
            Date date=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            //私钥及加密算法
            Algorithm algorithm=Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header=new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            //附带username和userid信息,存储到token中，生成签名
            return JWT.create()
                    .withHeader(header)
                    //存储自己想要留存给客户端浏览器的内容
                    .withClaim("userId",userId)
                    .withClaim("userName",userName)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void namespa(){
        System.out.println(TokenSign.signToken("1466649452","123"));

    }

    /**
     * token校验 是否正确
     * @param token
     * @return
     */

    public static boolean verifyToken(String token){

        try {
            Algorithm algorithm=Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier =JWT.require(algorithm).build();
            //此方法若token验证失败会抛错的，所以直接return true没问题
            DecodedJWT decodedJWT =verifier.verify(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取token中信息 userName
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            e.getStackTrace();
        }
        return null;
    }

    /**
     * 获取token中信息 userId
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            e.getStackTrace();

        }
        return null;
    }

}
