package com.scu.exam.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Classes;
import com.scu.exam.pojo.School;
import com.scu.exam.pojo.Score;
import com.scu.exam.pojo.Student;
import com.scu.exam.service.*;
import com.scu.exam.utils.JsonOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        JSONObject scorejson=(JSONObject)JSONObject.toJSON(score);
        if(score!=null){
            //有该记录
            Student student=(Student)studentService.findStudentById(stu_id);
            com.scu.exam.pojo.Test testpaper=(com.scu.exam.pojo.Test)testService.findByPid(score.getPaper_id());
            Classes stu_class=(Classes) classesService.findClassById(student.getClass_id());
            School school=(School) schoolService.findSchoolByName(stu_class.getSchool());

            //转格式合并
            JSONObject studentjson=(JSONObject)JSONObject.toJSON(student);
            JsonOperation.combineJson(scorejson,studentjson);

            JSONObject testpaperjson=(JSONObject)JSONObject.toJSON(testpaper);
            JsonOperation.combineJson(scorejson,testpaperjson);
            JSONObject stu_classjson=(JSONObject)JSONObject.toJSON(stu_class);
            JsonOperation.combineJson(scorejson,stu_classjson);
            JSONObject schooljson=(JSONObject)JSONObject.toJSON(school);
            JsonOperation.combineJson(scorejson,schooljson);
        }
        System.out.println(scorejson);
        System.out.println("查询结束");
    }
    @Test
    public void getPaperScoreInfo(){
        Integer paper_id=1;

        //获取该试卷相关的学生成绩
        List<Score> scoreList = scoreService.findScoreBypaperid(paper_id);
        Iterator<Score> scoreIterator = scoreList.iterator();
        Score temp;
        List<JSONObject> studentsinfo = new ArrayList<>();
        while (scoreIterator.hasNext()) {
            temp = scoreIterator.next();
            studentsinfo.add(this.getCompleteInfoBystu_idandPaper_id(temp.getStu_id(), paper_id));
        }
        JSONArray testinfojson = (JSONArray) JSONArray.toJSON(studentsinfo);
        System.out.println(testinfojson);

    }
    private JSONObject getCompleteInfoBystu_idandPaper_id(String stu_id, Integer paper_id) {
        Score score = (Score) scoreService.findOneScore(stu_id, paper_id);
        JSONObject scorejson = (JSONObject) JSONObject.toJSON(score);
        if (score != null) {
            //有该记录
            Student student = (Student) studentService.findStudentById(stu_id);
            com.scu.exam.pojo.Test testpaper = (com.scu.exam.pojo.Test) testService.findByPid(score.getPaper_id());
            Classes stu_class = (Classes) classesService.findClassById(student.getClass_id());
            School school = (School) schoolService.findSchoolByName(stu_class.getSchool());
            //转格式合并
            JSONObject studentjson = (JSONObject) JSONObject.toJSON(student);
            JsonOperation.combineJson(scorejson, studentjson);
            JSONObject testpaperjson = (JSONObject) JSONObject.toJSON(testpaper);
            JsonOperation.combineJson(scorejson, testpaperjson);
            JSONObject stu_classjson = (JSONObject) JSONObject.toJSON(stu_class);
            JsonOperation.combineJson(scorejson, stu_classjson);
            JSONObject schooljson = (JSONObject) JSONObject.toJSON(school);
            JsonOperation.combineJson(scorejson, schooljson);
        }
        return scorejson;
    }
}
