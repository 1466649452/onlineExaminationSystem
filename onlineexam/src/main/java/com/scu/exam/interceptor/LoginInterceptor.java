package com.scu.exam.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.utils.ResponseUtils;
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
        //设置登陆地址
        String loginAddr="www.baidu.com";
        System.out.println("进入拦截器");
        try {
            httpServletResponse.setCharacterEncoding("UTF-8");
            String token = httpServletRequest.getHeader("accessToken");
            if(token==null){
                Cookie[] cookies=httpServletRequest.getCookies();
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("accessToken")){
                        token=cookie.getValue();
                        break;
                    }else{
                        token=null;
                    }
                }
            }
            JSONObject res=new JSONObject();
            if (token == null) {
                System.out.println("没有accessToken，登陆校验未通过");
                res.put("status", "fail");
                ResponseUtils.renderJson(httpServletResponse,res);
            } else {
                boolean result = TokenSign.verifyToken(token);
                if (result) {
                    System.out.println("校验通过");
                    //更新存储的token信息
                    TokenConstant.updateTokenMap(token);
                    return true;
                }
                System.out.println("accessToken错误，登陆校验未通过");
                res.put("status", "fail");
                ResponseUtils.renderJson(httpServletResponse,res);
            }
            httpServletResponse.setStatus(302);
            httpServletResponse.setHeader("Location", loginAddr);
            return false;
        }catch (Exception e){
            e.printStackTrace();
            //返回到登陆界面
            httpServletResponse.setStatus(302);
            httpServletResponse.setHeader("Location", loginAddr);
            return false;
        }
    }


}
