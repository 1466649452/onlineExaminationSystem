package com.scu.exam.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.scu.exam.pojo.*;
import com.scu.exam.service.*;
import com.scu.exam.utils.JsonOperation;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Api(tags = "考试成绩相关API")
@Controller
@RequestMapping("score")
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


    /*
     * 查询学生情况相关API
     * */
    @ApiOperation("获取一位考生某次考试的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stu_id", dataType = "String", required = true, value = "用户Id"),
            @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "试卷id")
    })
    @GetMapping("/getstudentScore")
    public void getScoreInfo(String stu_id, Integer paper_id, HttpServletResponse response) {
        System.out.println("获取一位考生某次考试的信息");
        System.out.println(stu_id + " and " + paper_id);
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
        JSONObject teacherinfojson = (JSONObject) JSONObject.toJSON(teacherinfo);
        JsonOperation.combineJson(testinfojson, teacherinfojson);

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
        testinfojson.put("studentscores", studentsinfojson);


        ResponseUtils.renderJson(response, teacherinfojson);
    }

    @ApiOperation("分数区间人数统计")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "试卷id"),
            @ApiImplicitParam(paramType = "query", name = "startScore", dataType = "Double", required = true, value = "试卷id"),
            @ApiImplicitParam(paramType = "query", name = "endScore", dataType = "Double", required = true, value = "试卷id")
    })
    @GetMapping("/countStudentBetween")
    public void countStudentBetween(Integer paper_id, Double startScore, Double endScore, HttpServletResponse response) {
        List<Score> scoreListmore = scoreService.findScoreCompare(paper_id, startScore, "more");
        List<Score> scoreListless = scoreService.findScoreCompare(paper_id, endScore, "less");
        List<Score> returnList = new ArrayList<>();
        Iterator<Score> itscore = scoreListmore.iterator();
        while (itscore.hasNext()) {
            Score temp = itscore.next();
            if (scoreListless.contains(temp)) {
                returnList.add(temp);
            }
        }
        //加两个边界
        List<Score> scoreequal1 = scoreService.findScoreCompare(paper_id, startScore, "equal");
        itscore = scoreequal1.iterator();
        while (itscore.hasNext()) {
            returnList.add(itscore.next());
        }
        List<Score> scoreequal2 = scoreService.findScoreCompare(paper_id, startScore, "equal");
        itscore = scoreequal2.iterator();
        while (itscore.hasNext()) {
            returnList.add(itscore.next());
        }
        JSONArray betweenjson = (JSONArray) JSONArray.toJSON(returnList);
        ResponseUtils.renderJson(response, betweenjson);
    }

    /*
     * 添加数据相关api
     * */
    @ApiOperation("添加学生考试记录")
    @PostMapping("/addRecordOfScore")
    public void addRecordOfScore(@RequestBody JSONObject data, HttpServletResponse response) throws JsonProcessingException {
        JSONObject js=(JSONObject) JSONObject.toJSON(data.get("record"));
        //必要数据校验 stu_id,paper_id
        if(js.get("stu_id")==null||js.get("paper_id")==null){
            JSONObject res=new JSONObject();
            res.put("status","fail");
            res.put("error","没有传入必要信息");
            ResponseUtils.renderJson(response,res);
            return;
        }
        //String stu_id, int paper_id, double score, Date finish, JSONObject stu_ans
        Score insertScore=new Score((String)js.get("stu_id"),(Integer)js.get("paper_id"),(Double) js.get("score"),(Long)js.get("finish"),(JSONObject)js.get("stu_ans"));
        if(scoreService.findOneScore(insertScore.getStu_id(),insertScore.getPaper_id())!=null){
            JSONObject res=new JSONObject();
            res.put("status","fail");
            res.put("error","该学生已经做过该题");
            ResponseUtils.renderJson(response,res);
            return;
        }
        int insertnumber=scoreService.insertOneScore(insertScore);
        if(insertnumber!=0){
            JSONObject res=new JSONObject();
            res.put("status","success");
            ResponseUtils.renderJson(response,res);
        }else{
            JSONObject res=new JSONObject();
            res.put("status","fail");
            res.put("error","未知错误");
            ResponseUtils.renderJson(response,res);
        }
    }

    @ApiOperation("添加学生考试记录")
    @PostMapping("/updateOneScore")
    public void updateTestScore(@RequestBody JSONObject data, HttpServletResponse response){
        JSONObject js=(JSONObject) JSONObject.toJSON(data.get("record"));
        //必要数据校验 stu_id,paper_id
        if(js.get("stu_id")==null||js.get("paper_id")==null){
            JSONObject res=new JSONObject();
            res.put("status","fail");
            res.put("error","没有传入必要信息");
            ResponseUtils.renderJson(response,res);
            return;
        }
        //String stu_id, int paper_id, double score, Date finish, JSONObject stu_ans
        Score insertScore=new Score((String)js.get("stu_id"),(Integer)js.get("paper_id"),(Double) js.get("score"),(Long)js.get("finish"),(JSONObject)js.get("stu_ans"));
        Score oldscore=scoreService.findOneScore(insertScore.getStu_id(),insertScore.getPaper_id());

        //修改成绩
        if(insertScore.getScore()!=oldscore.getScore()&&js.get("score")!=null){
            scoreService.updateScore(insertScore.getStu_id(),insertScore.getPaper_id(),insertScore.getScore());
        }

    }

    @ApiOperation("删除学生考试记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stu_id", dataType = "String", required = true, value = "用户Id"),
            @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "试卷id")
    })
    @PostMapping("/deleteOneScore")
    public void deleteOneScore(Integer paper_id,String stu_id, HttpServletResponse response){
        int t=scoreService.deleteOneScore(stu_id,paper_id);
        JSONObject js=new JSONObject();
        if(t!=0){
            js.put("status","success");
            ResponseUtils.renderJson(response,js);
        }else{
            js.put("status","fail");
            ResponseUtils.renderJson(response,js);
        }
    }

    @ApiOperation("删除一位学生所有考试记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stu_id", dataType = "String", required = true, value = "用户Id")
    })
    @PostMapping("/deleteStudentScore")
    public void deleteStudentScore(String stu_id, HttpServletResponse response){
        int t=scoreService.deleteBystuid(stu_id);
        JSONObject js=new JSONObject();
        if(t!=0){
            js.put("status","success");
            ResponseUtils.renderJson(response,js);
        }else{
            js.put("status","fail");
            ResponseUtils.renderJson(response,js);
        }
    }

    @ApiOperation("删除一场考试的所有考试记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "用户Id")
    })
    @PostMapping("/deleteTestScore")
    public void deleteTestScore(Integer paper_id, HttpServletResponse response){
        int t=scoreService.deleteBypaperid(paper_id);
        JSONObject js=new JSONObject();
        if(t!=0){
            js.put("status","success");
            ResponseUtils.renderJson(response,js);
        }else{
            js.put("status","fail");
            ResponseUtils.renderJson(response,js);
        }
    }

}