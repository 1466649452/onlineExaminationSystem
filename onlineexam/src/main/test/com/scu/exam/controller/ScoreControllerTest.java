package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Classes;
import com.scu.exam.pojo.School;
import com.scu.exam.pojo.Score;
import com.scu.exam.pojo.Student;
import com.scu.exam.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ScoreControllerTest {
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


    @Test
    public void getScoreInfoTest(){
        String stu_id="1";
        Integer paper_id=1;

        System.out.println("获取学生信息");

        Score score=(Score)scoreService.findOneScore(stu_id,paper_id);
        if(score!=null){
            Student student=(Student)studentService.findStudentById(stu_id);
            com.scu.exam.pojo.Test testpaper=(com.scu.exam.pojo.Test)testService.findByPid(score.getPaper_id());
            Classes stu_class=(Classes) classesService.findClassById(student.getClass_id());
            School school=(School) schoolService.findSchoolByName(stu_class.getSchool());
        }


/**
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
*/
        System.out.println("查询结束");
    }
}
