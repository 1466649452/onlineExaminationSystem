package com.scu.exam.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {
    public static void renderJson(HttpServletResponse response, String text) {
        // System.out.print(text);
        render(response, "text/plain;charset=UTF-8", text);
    }

    public static void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
        }
    }
}