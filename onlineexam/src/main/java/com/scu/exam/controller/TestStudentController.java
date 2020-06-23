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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("student")
public class TestStudentController {
    @Autowired
    private TestService testService;
    @Autowired
    private CorrectRateService correctRateService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @ApiOperation("显示试卷")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "paper_id", dataType = "Integer", required = true, value = "试卷Id"),
    })
    @GetMapping("/test")
    @ResponseBody
    public void testList (Integer paper_id, HttpServletResponse response) {
       //卷子信息
        Test test = testService.findByPid(paper_id);
        JSONObject testjson = (JSONObject) JSONObject.toJSON(test);

        List<CorrectRate> correctRateList = correctRateService.findByPid(paper_id);
        int n = correctRateList.size();
        List<JSONObject> jsonQuestionAndAnswerList = new ArrayList<>();
        jsonQuestionAndAnswerList.add(testjson);

        for(int i=0; i<n; i++){
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
            for(int j=0; j<list.size(); j++){
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

}
