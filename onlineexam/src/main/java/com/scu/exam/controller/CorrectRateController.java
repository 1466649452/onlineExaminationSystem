package com.scu.exam.controller;

import com.scu.exam.pojo.Answer;
import com.scu.exam.pojo.CorrectRate;
import com.scu.exam.pojo.Question;
import com.scu.exam.pojo.Test;
import com.scu.exam.service.AnswerService;
import com.scu.exam.service.CorrectRateService;
import com.scu.exam.service.QuestionService;
import com.scu.exam.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("teacher")
public class CorrectRateController {

    @Autowired
    private CorrectRateService correctRateService;
    @Autowired
    private TestService testService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @GetMapping("/toTest")
    public String toTest(int paper_id, ModelMap modelMap){
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
        return "Test";
    }



}
