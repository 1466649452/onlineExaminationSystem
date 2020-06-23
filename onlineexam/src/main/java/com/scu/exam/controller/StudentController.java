package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Administrator;
import com.scu.exam.pojo.Student;
import com.scu.exam.service.StudentService;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Api(tags = "学生信息相关API")
@Controller
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    //添加学生信息
    @ApiOperation("添加学生信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/addStudent")
    public void addAdmin(@RequestBody JSONObject studentInfo, HttpServletResponse response){
        System.out.println(studentInfo.toString());

        Student student = setStudentByJSON(studentInfo);
        Student student1 = studentService.findStudentById(student.getStu_id());

        if(student1.getStu_idcard().equals(student1.getStu_idcard())){
            ResponseUtils.renderJson(response, "失败！该用户已存在！");
        }else{
            try{
                studentService.insertStudent(student);
            }catch (DataAccessException e){
                ResponseUtils.renderJson(response, "注册失败！！！");
            }
        }
        ResponseUtils.renderJson(response, "注册成功！！！！");

        System.out.println(response);
    }

    //查看学生信息
    @ApiOperation("查看学生个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stu_id", dataType = "String", required = true, value = "学生ID")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/getStudent")
    public void getStudentInfo(String stu_id, HttpServletResponse response){
        Student student = studentService.findStudentById(stu_id);
        if (student != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("student", student);
            ResponseUtils.renderJson(response, jsonObject);
        }

        //测试用，待删
        System.out.println(response);
    }

    //修改学生信息
    @ApiOperation("管理员修改学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stu_id", dataType = "String", required = true, value = "学生id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/updateStudent")
    public void updateAdminInfo(@RequestBody JSONObject studentInfo, HttpServletResponse response){
        System.out.println(studentInfo);
        Student student = setStudentByJSON(studentInfo);

        /* 在数据库修改相对应的学生信息 */
        try{
            System.out.println(studentService.updateStudent(student));
            ResponseUtils.renderJson(response, "修改成功！");
        }catch (DataAccessException e){
            System.out.println("modigy");
            ResponseUtils.renderJson(response, "修改失败！");
        }
    }

    //删除学生信息
    @ApiOperation("删除学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stu_id", dataType = "String", required = true, value = "学生id")
    })
    @PostMapping("/deleteStudent")
    public void deleteOneScore(String stu_id, HttpServletResponse response){
        int stu=studentService.deleteStudentById(stu_id);
        JSONObject js=new JSONObject();
        if(stu!=0){
            js.put("status","success");
            ResponseUtils.renderJson(response,js);
        }else{
            js.put("status","fail");
            ResponseUtils.renderJson(response,js);
        }
    }

    /*
       将前端传回的json对象中信息取出存入student对象中
    */
    public Student setStudentByJSON(JSONObject jsonObject){
        Student student = new Student();
        Administrator administrator = new Administrator();
        student.setStu_birthdate((Date) jsonObject.get("stu_birthdate"));
        student.setStu_password((String)jsonObject.get("stu_password"));
        student.setStu_name((String)jsonObject.get("stu_name"));
        student.setClass_id((String)jsonObject.get("class_id"));
        student.setStu_id((String)jsonObject.get("stu_id"));
        student.setStu_idcard((String)jsonObject.get("stu_idcard"));
        student.setStu_Image((String)jsonObject.get("stu_Image"));

        return student;
    }
}
