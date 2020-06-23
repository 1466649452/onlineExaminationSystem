package com.scu.exam.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Answer;
import com.scu.exam.pojo.CorrectRate;
import com.scu.exam.pojo.Question;
import com.scu.exam.pojo.Test;
import com.scu.exam.service.AnswerService;
import com.scu.exam.service.CorrectRateService;
import com.scu.exam.service.QuestionService;
import com.scu.exam.service.TestService;
import com.scu.exam.utils.JsonOperation;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    public void testList(String t_id, HttpServletResponse response) {
        System.out.println(t_id);
        List<Test> list = testService.findByTid(t_id);
        if (list.size() == 0) {
            JSONObject res = new JSONObject();
            res.put("status", "fail");
            res.put("error", "您未上传任何试卷");
            ResponseUtils.renderJson(response, res);
        } else {
            JSONArray testlist = (JSONArray) JSONArray.toJSON(list);
            ResponseUtils.renderJson(response, testlist);
        }
    }

    @ApiOperation("添加试卷")
    @PostMapping("/addTest")
    @ResponseBody
    public void addTest(@RequestBody JSONObject data, HttpServletResponse response) {
        try {
            JSONObject js = (JSONObject) JSONObject.toJSON(data.get("test"));
            if (js.get("t_id") == null || js.get("start") == null || js.get("paper_name") == null) {
                JSONObject res = new JSONObject();
                res.put("status", "fail");
                res.put("error", "没有传入必要信息");
                ResponseUtils.renderJson(response, res);
            }
            //插入test数据库
            Test test = new Test();
            test.setT_id((String) js.get("t_id"));
            test.setPaper_name((String) js.get("paper_name"));
            //注意类型转换
            Timestamp timestamp = new Timestamp((long) js.get("start"));
            test.setStart(timestamp);
            if (js.get("end") != null) {
                Timestamp time = new Timestamp((long) js.get("end"));
                test.setEnd(time);
            }
            int result1 = testService.insertTest(test);
            System.out.println(result1);
            System.out.println(test.getPaper_id());
            //插入correctrate数据库
            List<CorrectRate> correctRateList = new ArrayList<CorrectRate>();
            int p_id = test.getPaper_id();
            JSONArray jsonArray = (JSONArray) js.get("arraylist");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                CorrectRate correctRate = new CorrectRate();
                correctRate.setPaper_id(p_id);
                correctRate.setQuestion_id((Integer) jsonObject.get("question_id"));
                correctRate.setPoint((Integer) jsonObject.get("point"));
                correctRateList.add(correctRate);
            }
            System.out.println(correctRateList);
            int result2 = correctRateService.insertCorrectRateBatch(correctRateList);
            System.out.println(result2);
            if (result1 == 1 && result2 == 1) {
                JSONObject res = new JSONObject();
                res.put("status", "success");
                ResponseUtils.renderJson(response, res);
            } else {
                JSONObject res = new JSONObject();
                res.put("status", "fail");
                res.put("error", "插入不成功");
                ResponseUtils.renderJson(response, res);
            }
        } catch (Exception e) {
            JSONObject res = new JSONObject();
            res.put("status", "fail");
            res.put("error", "插入不成功");
            ResponseUtils.renderJson(response, res);
        }

    }

    //老师修改试卷界面所需奥的信息
    @ApiOperation("老师修改试卷")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "试卷Id"),
    })
    @GetMapping("/testInfo")
    @ResponseBody
    public void testList(Integer paper_id, HttpServletResponse response) {
        //卷子信息
        Test test = testService.findByPid(paper_id);
        JSONObject testjson = (JSONObject) JSONObject.toJSON(test);

        List<CorrectRate> correctRateList = correctRateService.findByPid(paper_id);
        int n = correctRateList.size();
        List<JSONObject> jsonQuestionAndAnswerList = new ArrayList<>();
        jsonQuestionAndAnswerList.add(testjson);

        for (int i = 0; i < n; i++) {
            int question_id = correctRateList.get(i).getQuestion_id();

            //一道题的信息（除了选项）
            Question question = questionService.findQuestionById(question_id);
            CorrectRate correctRate = correctRateService.findByAllId(question_id, paper_id);
            JSONObject questionjson = (JSONObject) JSONObject.toJSON(question);
            JSONObject correctratejson = (JSONObject) JSONObject.toJSON(correctRate);
            JsonOperation.combineJson(correctratejson, questionjson);

            // 一道题的选项
            List<Answer> list = new ArrayList<>();
            list = answerService.findAnswerById(question_id);
            List<JSONObject> AnswerList = new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                JSONObject answer = (JSONObject) JSONObject.toJSON(list.get(j));
                AnswerList.add(answer);
            }
            JSONArray answerlistjson = (JSONArray) JSONArray.toJSON(AnswerList);
            correctratejson.put("answerlist", answerlistjson);
            jsonQuestionAndAnswerList.add(correctratejson);
        }
        JSONArray QuestionAndAnswerList = (JSONArray) JSONArray.toJSON(jsonQuestionAndAnswerList);
        ResponseUtils.renderJson(response, QuestionAndAnswerList);
    }


    //老师修改试卷信息后，后端数据库的处理
    @ApiOperation("修改试卷信息")
    @PostMapping("/updateTest")
    @ResponseBody
    public void updatetest(@RequestBody JSONObject data, HttpServletResponse response) {
        JSONObject js = (JSONObject) JSONObject.toJSON(data.get("updateInfo"));
        if (js.get("paper_id") == null) {
            JSONObject res = new JSONObject();
            res.put("status", "fail");
            res.put("error", "没有传入必要信息");
            ResponseUtils.renderJson(response, res);
        } else {
            Integer p_id = (Integer) js.get("paper_id");
            if (js.get("paper_name") != null) {
                Test test = testService.findByPid(p_id);
                test.setPaper_name((String) js.get("paper_name"));
                int result1 = testService.updateTest(test);
                if (result1 == 1) {
                    ResponseUtils.renderJson(response, "修改试卷名成功");
                }
            }
            if (js.get("start") != null) {
                Test test = testService.findByPid(p_id);
                Timestamp timestamp = new Timestamp((long) js.get("start"));
                test.setStart(timestamp);
                int result2 = testService.updateTest(test);
                if (result2 == 1) {
                    ResponseUtils.renderJson(response, "修改开始时间成功");
                }
            }
            if (js.get("end") != null) {
                Test test = testService.findByPid(p_id);
                Timestamp time = new Timestamp((long) js.get("end"));
                test.setEnd(time);
                int result3 = testService.updateTest(test);
                if (result3 == 1) {
                    ResponseUtils.renderJson(response, "修改结束时间成功");
                }
            }
            //arrayList: question_id,point,type(delete:0;add:1;change:3)
            if (js.get("arraylist") != null) {
                JSONArray jsonArray = (JSONArray) js.get("arraylist");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    int change = (Integer) jsonObject.get("change");
                    //删除该卷的某道题
                    if (change == 0) {
                        int result4 = correctRateService.deleteCorrectRate((Integer) jsonObject.get("question_id"), p_id);
                        if (result4 == 1) {
                            ResponseUtils.renderJson(response, "删除题目成功");
                        }
                    }
                    //添加题目
                    if (change == 1) {
                        CorrectRate correctRate = new CorrectRate();
                        correctRate.setPoint((Integer) jsonObject.get("point"));
                        correctRate.setQuestion_id((Integer) jsonObject.get("question_id"));
                        correctRate.setPaper_id(p_id);
                        int result5 = correctRateService.insertCorrectRate(correctRate);
                        if (result5 == 1) {
                            ResponseUtils.renderJson(response, "添加题目成功");
                        }
                    }
                    //修改某道题
                    if (change == 3) {
                        CorrectRate correctRate = new CorrectRate();
                        correctRate.setPoint((Integer) jsonObject.get("point"));
                        correctRate.setQuestion_id((Integer) jsonObject.get("question_id"));
                        correctRate.setPaper_id(p_id);
                        int result6 = correctRateService.updateCorrectRate(correctRate);
                        if (result6 == 1) {
                            ResponseUtils.renderJson(response, "修改题目成功");
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
    public void deleteOneTest(@RequestBody JSONObject data, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        try {

            int result1 = correctRateService.deleteCorrectRateBatch((Integer) data.get("paper_id"));
            int result2 = testService.deleteTest((Integer) data.get("paper_id"));
            if (result1 == 1 && result2 == 1) {
                res.put("status", "success");
            } else {
                System.out.println("删除错误");
                res.put("status", "fail");
            }
            ResponseUtils.renderJson(response, res);
        } catch (Exception e) {
            System.out.println("删除异常错误");
            res.put("status", "fail");
            ResponseUtils.renderJson(response, res);
        }
    }

    @ApiOperation("删除老师发布的所有试卷")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "t_id", dataType = "String", required = true, value = "老师id")
    })
    @PostMapping("/deleteAllTest")
    @ResponseBody
    public void deleteAllTest(String t_id, HttpServletResponse response) {
        List<Test> testList = testService.findByTid(t_id);
        for (int i = 0; i < testList.size(); i++) {
            int p_id = testList.get(i).getPaper_id();
            correctRateService.deleteCorrectRateBatch(p_id);
        }
        int result = testService.deleteAllByTid(t_id);

        if (result == 1) {
            ResponseUtils.renderJson(response, "删除所有试卷成功");
        } else {
            ResponseUtils.renderJson(response, "删除所有试卷失败");
        }
    }


}

