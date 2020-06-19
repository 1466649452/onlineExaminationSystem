package com.scu.exam.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.*;
import com.scu.exam.service.*;
import com.scu.exam.utils.JsonOperation;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Api(tags = "从考试获取信息")
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
    @Autowired
    private TeacherService teacherService;

    @ApiOperation("获取一位考生某次考试的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stu_id", dataType = "String", required = true, value = "用户Id"),
            @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "试卷id")
    })
    @GetMapping("/getstudentScore")
    public void getScoreInfo(String stu_id, Integer paper_id, HttpServletResponse response) {
        System.out.println("获取一位考生某次考试的信息");
        System.out.println(stu_id+" and "+paper_id);
        JSONObject returnjsonobj = this.getCompleteInfoBystu_idandPaper_id(stu_id, paper_id);
        System.out.println("查询结束");
        ResponseUtils.renderJson(response, returnjsonobj);
    }
    //获取学生某次考试的完整信息，包含姓名等

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

    @ApiOperation("获取某次考试的所有学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "试卷id")
    })
    @GetMapping("/getPaperStudentScoreInfo")
    public void getPaperStudentScoreInfo(Integer paper_id, HttpServletResponse response) {
        //testinfojson同时作为返回的容器
        Test testinfo = testService.findByPid(paper_id);
        JSONObject testinfojson = (JSONObject) JSONObject.toJSON(testinfo);
        if (testinfo == null) {
            //没有该试卷
            ResponseUtils.renderJson(response, "fail");
            return;
        }
        //获取试卷相关信息
        Teacher teacherinfo = teacherService.findTeacherById(testinfo.getT_id());
        JSONObject teacherinfojson=(JSONObject) JSONObject.toJSON(teacherinfo);
        JsonOperation.combineJson(testinfojson,teacherinfojson);

        //获取该试卷相关的学生成绩
        List<Score> scoreList = scoreService.findScoreBypaperid(paper_id);
        Iterator<Score> scoreIterator = scoreList.iterator();
        Score temp;
        List<JSONObject> studentsinfo = new ArrayList<>();
        while (scoreIterator.hasNext()) {
            temp = scoreIterator.next();
            studentsinfo.add(this.getCompleteInfoBystu_idandPaper_id(temp.getStu_id(), paper_id));
        }

        JSONArray studentsinfojson = (JSONArray) JSONArray.toJSON(studentsinfo);
        testinfojson.put("studentscores",studentsinfojson);


        ResponseUtils.renderJson(response,teacherinfojson);
    }

    @ApiOperation("分数区间人数统计")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "试卷id"),
            @ApiImplicitParam(paramType = "query", name = "startScore", dataType = "Double", required = true, value = "试卷id"),
            @ApiImplicitParam(paramType = "query", name = "endScore", dataType = "Double", required = true, value = "试卷id")
    })
    @GetMapping("/countStudentBetween")
    public void countStudentBetween(Double startScore,Double endScore,HttpServletResponse response){

    }
}
