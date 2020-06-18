package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Score;
import com.scu.exam.pojo.Student;
import com.scu.exam.pojo.Test;
import com.scu.exam.service.ScoreService;
import com.scu.exam.service.StudentService;
import com.scu.exam.service.TestService;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "获取信息")
@Controller
@RequestMapping("info")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;
    private StudentService studentService;
    private TestService testService;

    @ApiOperation("获取一位考生某次考试的信息,")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stu_id", dataType = "String", required = true, value = "用户Id")
    })
    @GetMapping("/getstudentScore")
    public void getScoreInfo(String stu_id, HttpServletResponse response){
        System.out.println("获取学生信息");
        Score score=(Score)scoreService.findScoreBystuid(stu_id);
        Student student=(Student)studentService.findStudentById(stu_id);

        JSONObject json=new JSONObject();
        json.put("stu_id",student.getStu_id());
        json.put("stu_name",student.getStu_name());
        json.put("stu_classid",student.getClass_id());


    }


}
