package com.scu.exam.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {
    public static void renderJson(HttpServletResponse response, String text) {
        // System.out.print(text);
        render(response, "application/json;charset=UTF-8", text);
    }

    public static void renderJson(HttpServletResponse response, Object obj) {
        // System.out.print(text);
        render(response, "application/json;charset=UTF-8", obj.toString());
    }

    public static void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Access-Control-Allow-Origin","*");
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
        }
    }
}
