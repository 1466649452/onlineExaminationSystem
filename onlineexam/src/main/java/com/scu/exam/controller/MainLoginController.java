package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Administrator;
import com.scu.exam.pojo.Student;
import com.scu.exam.pojo.Teacher;
import com.scu.exam.service.AdministratorService;
import com.scu.exam.service.StudentService;
import com.scu.exam.service.TeacherService;
import com.scu.exam.utils.ResponseUtils;
import com.scu.exam.utils.TokenSign;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "登陆以及登陆状态校验")
@Controller
public class MainLoginController {

    @Autowired
    AdministratorService administratorService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;

    @ApiOperation("登陆请求,data中携带userId和userPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "String", required = true, value = "用户Id"),
            @ApiImplicitParam(paramType = "query", name = "userName", dataType = "String", required = true, value = "用户Password")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/login")//@RequestBody JSONObject data,//String userId,String userPassword,
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String successAddr="/htmlsite/pages/samples/index.html";
        String failAddr="/htmlsite/login.html";
        try{
            System.out.println("进入登陆验证...");
            String userId=request.getParameter("userId").toString();
            String userPassword=request.getParameter("userPassword").toString();
            //String userId=data.get("userId").toString();
            //String userPassword=data.get("userPassword").toString();
            System.out.println(userId+":"+userPassword);
            //数据库权限校验
            boolean loginsuccess = false;
            String identity = "";
            //逐个权限验证
            Teacher tea = null;
            Administrator admin = null;
            Student stu = (Student) studentService.findStudentById(userId);
            if (stu != null && stu.getStu_password().equals(userPassword)) {
                loginsuccess = true;
                identity = "student";
                System.out.println("找到学生" + loginsuccess + " " + identity);
            } else {
                tea = (Teacher) teacherService.findTeacherById(userId);
                if (tea != null && tea.getT_password().equals(userPassword)) {
                    loginsuccess = true;
                    identity = "teacher";
                    System.out.println("找到教师" + loginsuccess + " " + identity);
                } else {
                    admin = (Administrator) administratorService.findAdministratorById(userId);
                    if (admin != null && admin.getAd_password().equals(userPassword)) {
                        loginsuccess = true;
                        identity = "admin";
                        System.out.println("找到管理员" + loginsuccess + " " + identity);
                    }
                }
            }
            if (loginsuccess == true) {
                System.out.println("登陆验证通过...");
                //登陆验证通过
                String userName = "";
                switch (identity) {
                    case "student":
                        userName = stu.getStu_name();
                        break;
                    case "teacher":
                        userName = tea.getT_name();
                        break;
                    case "admin":
                        userName = admin.getAd_name();
                        break;
                }
                System.out.println("产生token中...");
                //产生token
                String accessToken = TokenSign.signToken(userId, userName,identity);
                Cookie cookie = new Cookie("accessToken", accessToken);
                //两个小时过期
                cookie.setMaxAge(TokenSign.EXPIRE_TIME);
                cookie.setPath("./");
                cookie.setDomain(request.getServerName());
                response.addCookie(cookie);
                JSONObject json = new JSONObject();
                json.put("status", "success");
                json.put("identity", identity);
                //验证通过
                //重定位到合适的页面
                response.sendRedirect(successAddr);
                ResponseUtils.renderJson(response, json);
            } else {
                //未通过
                System.out.println("登陆验证未通过...");
                JSONObject json = new JSONObject();
                json.put("status", "fail");
                //验证未通过
                //重定位到合适的页面
                response.sendRedirect(failAddr);
                ResponseUtils.renderJson(response, json);
            }
        }catch (Exception e){
            //未通过
            System.out.println("登陆验出错，未通过...");
            JSONObject json = new JSONObject();
            json.put("status", "fail");

            //验证未通过
            //重定位到合适的页面
            response.sendRedirect(failAddr);
            ResponseUtils.renderJson(response, json);
        }


    }


}
