package com.scu.exam.controller;

import com.scu.exam.pojo.Question;
import com.scu.exam.service.QuestionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class QuestionControllerTest {
    @Autowired
    private QuestionController questionController;
    @Autowired
    private QuestionService questionService;

    @Before
    public void setUp() throws Exception {
        Question question1 = new Question(111111, "sfwefwe", "dwefef", "d");
        questionService.insertQuestion(question1);
        Question question2 = new Question(222222, "feferf", "dsdfew", "cdsfe");
        questionService.insertQuestion(question2);
    }

    @After
    public void tearDown() throws Exception {
        List<Question> questionList = questionService.findAllQuestion();
        System.out.println("数据库中的题目为");
        for (int i = 0; i < questionList.size(); i++){
            System.out.println(questionList.get(i).toString());
            questionService.deleteQuestion(questionList.get(i));
        }
    }

    @Test
    public void addQuestion(HttpServletResponse response) {
        System.out.println("开始测试addQuestion():");

        System.out.println("插入符合规范的题目：");
        System.out.println("期望输出：插入成功后的数据库信息");

        System.out.println("测试结束");
    }

    @Test
    public void updateQuestion() {
        System.out.println("开始测试updateQuestion():");


        System.out.println("测试结束");
    }

    @Test
    public void getQuestionById() {
        System.out.println("开始测试getQuestionById():");


        System.out.println("测试结束");
    }

    @Test
    public void getQuestionByKeyword() {
        System.out.println("开始测试getQuestionByKeyword():");


        System.out.println("测试结束");
    }

    @Test
    public void deleteQuestion() {
        System.out.println("开始测试deleteQuestion():");


        System.out.println("测试结束");
    }
}