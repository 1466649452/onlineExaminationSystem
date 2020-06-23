package com.scu.exam.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.*;
import com.scu.exam.service.AnswerService;
import com.scu.exam.service.CorrectRateService;
import com.scu.exam.service.QuestionService;
import com.scu.exam.service.TestService;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Api(tags = "试卷管理相关API")
@Controller
@RequestMapping("teacher")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private CorrectRateService correctRateService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @ApiOperation("获取老师发布试卷列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "t_id", dataType = "String", required = true, value = "老师Id"),
    })
    @GetMapping("/testList")
    @ResponseBody
    public void testList (String t_id, HttpServletResponse response) {
        System.out.println(t_id);
        List<Test> list = testService.findByTid(t_id);
       if(list.size() == 0){
           JSONObject res=new JSONObject();
           res.put("status","fail");
           res.put("error","您未上传任何试卷");
           ResponseUtils.renderJson(response,res);
       }else {
           JSONArray testlist = (JSONArray) JSONArray.toJSON(list);
           ResponseUtils.renderJson(response, testlist);
           }
    }

    @ApiOperation("添加试卷")
    @PostMapping("/addTest")
    @ResponseBody
    public void addTest(@RequestBody JSONObject data, HttpServletResponse response){
        JSONObject js=(JSONObject) JSONObject.toJSON(data.get("test"));
        if(js.get("t_id")==null||js.get("start")==null||js.get("paper_name")==null){
            JSONObject res=new JSONObject();
            res.put("status","fail");
            res.put("error","没有传入必要信息");
            ResponseUtils.renderJson(response,res);
            return;
        }
        //插入test数据库
        Test test = new Test();
        test.setT_id((String) js.get("t_id"));
        test.setPaper_name((String) js.get("paper_name"));
        //注意类型转换
        test.setStart((Timestamp) js.get("start"));
        if(js.get("end")!=null) {
            test.setStart((Timestamp) js.get("end"));
        }
        int result1 = testService.insertTest(test);
        System.out.println(result1);
        System.out.println(test.getPaper_id());

        //插入correctrate数据库
        //question_id and point 二维数组
        List<CorrectRate> correctRateList = new ArrayList<CorrectRate>();
        int a[][]= (int[][]) js.get("arraylist");
        int p_id = test.getPaper_id();
        for(int i=0; i<a.length; i++){
            CorrectRate correctRate = new CorrectRate();
            correctRate.setPaper_id(p_id);
            correctRate.setQuestion_id(a[i][0]);
            correctRate.setPoint(a[i][1]);
            correctRateList.add(correctRate);
        }
        System.out.println(correctRateList);
        int result2 = correctRateService.insertCorrectRateBatch(correctRateList);
        System.out.println(result2);
        if(result1==1&&result2==1){
            JSONObject res=new JSONObject();
            res.put("status","success");
            ResponseUtils.renderJson(response,res);
        }else{
            JSONObject res=new JSONObject();
            res.put("status","fail");
            res.put("error","插入不成功");
            ResponseUtils.renderJson(response,res);
        }
    }


    @GetMapping("/toUpdate")
    public String toUpdate(Integer paper_id, ModelMap modelMap){
        Test test = testService.findByPid(paper_id);
        List<CorrectRate> correctRateList = correctRateService.findByPid(paper_id);
        modelMap.addAttribute("test", test);
        modelMap.addAttribute("correctRateList", correctRateList);
       //题目具体内容
        int n = correctRateList.size();
        List<Question> questionList = new ArrayList<Question>();
        List<Answer> list = new ArrayList<Answer>();
        List<List<Answer>> answerList = new ArrayList<List<Answer>>();
        for(int i=0; i<n; i++){
            int question_id = correctRateList.get(i).getQuestion_id();
            Question question = questionService.findQuestionById(question_id);
            questionList.add(question);
            list = answerService.findAnswerById(question_id);
            answerList.add(list);
        }
        modelMap.addAttribute("questionList", questionList);
        modelMap.addAttribute("answerList",answerList);
        return "update";
    }

    @ApiOperation("修改试卷信息")
    @PostMapping("/updateTest")
    @ResponseBody
    public void updatetest(@RequestBody JSONObject data, HttpServletResponse response){
        JSONObject js=(JSONObject) JSONObject.toJSON(data.get("updateInfo"));
        if(js.get("paper_id")==null){
            JSONObject res=new JSONObject();
            res.put("status","fail");
            res.put("error","没有传入必要信息");
            ResponseUtils.renderJson(response,res);
        }else{
            int p_id = (int) js.get("paper_id");
            if(js.get("paper_name")!=null){
                Test test = testService.findByPid(p_id);
                test.setPaper_name((String) js.get("paper_name"));
                int result1=testService.updateTest(test);
                if(result1==1){
                    ResponseUtils.renderJson(response,"修改试卷名成功");
                }
            }
            if(js.get("start")!=null){
                Test test = testService.findByPid(p_id);
                test.setPaper_name((String) js.get("start"));
                int result2=testService.updateTest(test);
                if(result2==1){
                    ResponseUtils.renderJson(response,"修改开始时间成功");
                }
            }
            if(js.get("end")!=null){
                Test test = testService.findByPid(p_id);
                test.setPaper_name((String) js.get("end"));
                int result3=testService.updateTest(test);
                if(result3==1){
                    ResponseUtils.renderJson(response,"修改结束时间成功");
                }
            }
            //arrayList: question_id,point,type(delete:0;add:1;change:3)
            if(js.get("arrayList")!=null){
                int a[][]= (int[][]) js.get("arrayList");
                for(int i=0; i<a.length; i++){
                    //删除该卷的某道题
                    if (a[i][2]==0){
                        int result4=correctRateService.deleteCorrectRate(a[i][0], p_id);
                        if(result4==1){
                            ResponseUtils.renderJson(response,"删除题目成功");
                        }
                    }
                    //添加题目
                    if (a[i][2]==1){
                        CorrectRate correctRate = new CorrectRate();
                        correctRate.setPoint(a[i][1]);
                        correctRate.setQuestion_id(a[i][0]);
                        correctRate.setPaper_id(p_id);
                        int result5=correctRateService.insertCorrectRate(correctRate);
                        if(result5==1){
                            ResponseUtils.renderJson(response,"添加题目成功");
                        }
                    }
                    //修改某道题
                    if(a[i][2]==3){
                        CorrectRate correctRate = new CorrectRate();
                        correctRate.setPoint(a[i][1]);
                        correctRate.setQuestion_id(a[i][0]);
                        correctRate.setPaper_id(p_id);
                        int result6=correctRateService.updateCorrectRate(correctRate);
                        if(result6==1){
                            ResponseUtils.renderJson(response,"修改题目成功");
                        }
                    }
                }
            }
        }
    }

    @ApiOperation("删除老师发布的一套试卷")
    @ApiImplicitParams({
             @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "试卷id")
    })
    @PostMapping("/deleteOneTest")
    @ResponseBody
    public void deleteOneTest(Integer paper_id, HttpServletResponse response){
        int result1=correctRateService.deleteCorrectRateBatch(paper_id);
        int result2=testService.deleteTest(paper_id);
        if(result1==1 && result2==1){
            ResponseUtils.renderJson(response, "删除该试卷成功");
        }else{
            ResponseUtils.renderJson(response, "删除该试卷失败");
        }
    }

    @ApiOperation("删除老师发布的所有试卷")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "t_id", dataType = "String", required = true, value = "老师id")
    })
    @PostMapping("/deleteAllTest")
    @ResponseBody
    public void deleteAllTest(String t_id, HttpServletResponse response){
        List<Test> testList=testService.findByTid(t_id);
        for(int i=0; i<testList.size(); i++){
            int p_id = testList.get(i).getPaper_id();
            correctRateService.deleteCorrectRateBatch(p_id);
        }
        int result = testService.deleteAllByTid(t_id);

        if(result==1){
            ResponseUtils.renderJson(response, "删除所有试卷成功");
        }else{
            ResponseUtils.renderJson(response, "删除所有试卷失败");
        }
    }


}

