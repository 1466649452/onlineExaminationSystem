package com.scu.exam.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.utils.TokenSign;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    //    在请求处理之前调用,只有返回true才会执行要执行的请求
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {

        System.out.println("进入拦截器");
        try {
            httpServletResponse.setCharacterEncoding("UTF-8");
            String token = httpServletRequest.getHeader("accessToken");
            if (token == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("data", "token is null");
                map.put("code", "401");
                System.out.println("进入处理");
                //httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
            } else {
                boolean result = TokenSign.verifyToken(token);
                if (result) {
                    System.out.println("校验通过");
                    //更新存储的token信息
                    TokenConstant.updateTokenMap(token);
                    //return ture则继续执行操作
                    return true;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("data", "token is null");
                map.put("code", "401");
                System.out.println("校验不通过");
                //httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
            }
            httpServletResponse.setStatus(302);
            httpServletResponse.setHeader("Location", "https://www.baidu.com");
            return false;
        }catch (Exception e){
            e.printStackTrace();
            //返回到登陆界面
            httpServletResponse.setStatus(302);
            httpServletResponse.setHeader("Location", "https://www.baidu.com");
            return false;
        }
    }


}
