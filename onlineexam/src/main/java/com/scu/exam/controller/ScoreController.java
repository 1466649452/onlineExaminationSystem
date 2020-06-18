package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.*;
import com.scu.exam.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "获取信息")
@Controller
@RequestMapping("info")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TestService testService;
    @Autowired
    private ClassesService classesService;
    @Autowired
    private SchoolService schoolService;

    @ApiOperation("获取一位考生某次考试的信息,")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stu_id", dataType = "String", required = true, value = "用户Id"),
            @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "试卷id")
    })
    @GetMapping("/getstudentScore")
    public void getScoreInfo(String stu_id,Integer paper_id, HttpServletResponse response){
        System.out.println("获取学生信息");

        Score score=(Score)scoreService.findOneScore(stu_id,paper_id);
        Student student=(Student)studentService.findStudentById(stu_id);
        Test testpaper=(Test)testService.findByPid(score.getPaper_id());
        Classes stu_class=(Classes) classesService.findClassById(student.getClass_id());
        School school=(School) schoolService.findSchoolByName(stu_class.getSchool());

        JSONObject scorejson=(JSONObject)JSONObject.toJSON(score);
        JSONObject studentjson=(JSONObject)JSONObject.toJSON(student);
        JSONObject testpaperjson=(JSONObject)JSONObject.toJSON(testpaper);
        JSONObject stu_classjson=(JSONObject)JSONObject.toJSON(stu_class);
        JSONObject schooljson=(JSONObject)JSONObject.toJSON(school);

        System.out.println(scorejson);
        System.out.println(studentjson);
        System.out.println(testpaperjson);
        System.out.println(stu_classjson);
        System.out.println(schooljson);

        System.out.println("查询结束");
    }


}
