package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Answer;
import com.scu.exam.pojo.Question;
import com.scu.exam.service.AnswerService;
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

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class QuestionControllerTest {
    @Autowired
    private QuestionController questionController;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @Before
    public void setUp() throws Exception {
        Question question1 = new Question(111111, "sfwefwe", "dwefef", "d");
        questionService.insertQuestion(question1);

        Answer answer1 = new Answer(111111, "aaaaaaaa");
        Answer answer2 = new Answer(111111, "bbbbbbbb");
        Answer answer3 = new Answer(111111, "cccccccc");
        Answer answer4 = new Answer(111111, "dddddddd");
        answerService.insertAnswer(answer1);
        answerService.insertAnswer(answer2);
        answerService.insertAnswer(answer3);
        answerService.insertAnswer(answer4);

        Question question2 = new Question(222222, "uuuuuuuu", "dwefef", "d");
        questionService.insertQuestion(question2);

        Answer answer5 = new Answer(222222, "aaaaaaaa");
        Answer answer6 = new Answer(222222, "bbbbbbbb");
        Answer answer7 = new Answer(222222, "cccccccc");
        Answer answer8 = new Answer(222222, "dddddddd");
    }

    @After
    public void tearDown() throws Exception {
        List<Question> questionList = questionService.findAllQuestion();
        System.out.println("数据库中的题目为");
        for (int i = 0; i < questionList.size(); i++){
            System.out.println(questionList.get(i).toString());
            questionService.deleteQuestion(questionList.get(i));
        }

        List<Answer> answerList  = answerService.findAllAnswer();
        System.out.println("数据库中的答案为");
        for (int i = 0; i < answerList.size(); i++){
            System.out.println(answerList.get(i).toString());
            answerService.deleteAnswer(answerList.get(i));
        }
    }

    public JSONObject initJSONObject(Integer question_id, String question_info, String correct_answer, String type, String answer_info){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("question_id", question_id);
        jsonObject.put("question_info", question_info);
        jsonObject.put("correct_answer", correct_answer);
        jsonObject.put("type", type);

        List<Answer> answerList = null;
        answerList.add(new Answer(question_id, answer_info));
        answerList.add(new Answer(question_id, "bbbbbbbbbb"));
        answerList.add(new Answer(question_id, "cccccccccc"));
        answerList.add(new Answer(question_id, "dddddddddd"));
        jsonObject.put("answerList", answerList);

        return jsonObject;
    }

    @Test
    public void addQuestion() {
        HttpServletResponse response = null;
        System.out.println("开始测试addQuestion():");

        System.out.println("插入符合规范的题目：");
        System.out.println("期望输出：插入成功！");
        JSONObject jsonObject1 = initJSONObject(null, "cccccc", "d", "q", "aaaaaa");
        questionController.addQuestion(jsonObject1, response);

        System.out.println("插入题库中已存在的题目：");
        System.out.println("期望输出：失败！该题目已存在！");
        JSONObject jsonObject2 = initJSONObject(null, "feferf", "d", "q", "aaaaaa");
        questionController.addQuestion(jsonObject2, response);

        System.out.println("测试结束");
    }

    @Test
    public void updateQuestion() {
        HttpServletResponse response = null;
        System.out.println("开始测试updateQuestion():");

        System.out.println("符合规范的修改题目：");
        System.out.println("期望输出：修改成功！");
        JSONObject jsonObject1 = initJSONObject(111111, "sdfwef", "gbrfter", "q", "zzzzzzzzzz");
        questionController.updateQuestion(jsonObject1, response);

        System.out.println("要修改的题目在数据库中不存在：");
        System.out.println("期望输出：修改失败！");
        JSONObject jsonObject2 = initJSONObject(333333, "dwsfew", "dsfw", "s", "nnnnnnnnn");
        questionController.updateQuestion(jsonObject2, response);

        System.out.println("修改后的题目在数据库中已存在：");
        System.out.println("期望输出：失败！该题目已存在！");
        JSONObject jsonObject3 = initJSONObject(111111, "uuuuuuuu", "dwefre", "d", "dfwe");
        questionController.updateQuestion(jsonObject3, response);

        System.out.println("测试结束");
    }

    @Test
    public void getQuestionById() {
        HttpServletResponse response = null;
        System.out.println("开始测试getQuestionById():");

        System.out.println("查找题库中存在的题目：");
        System.out.println("期望输出：查找成功！");
        questionController.getQuestionById(111111, response);

        System.out.println("查找题库中不存在的题目：");
        System.out.println("期望输出：查找失败！该题目不存在！");
        questionController.getQuestionById(333333, response);

        System.out.println("测试结束");
    }

//    @Test
//    public void getQuestionByKeyword() {
//        HttpServletResponse response = null;
//        System.out.println("开始测试getQuestionByKeyword():");
//
//        System.out.println("查找题库中存在的题目：");
//        System.out.println("期望输出：查找成功！");
//        questionController.getQuestionByKeyword("we", response);
//
//        System.out.println("没有与关键词相关的题目：");
//        System.out.println("期望输出：questionList为null");
//        questionController.getQuestionByKeyword("a", response);
//
//        System.out.println("测试结束");
//    }

    @Test
    public void deleteQuestion() {
        HttpServletResponse response = null;
        System.out.println("开始测试deleteQuestion():");

        System.out.println("删除题库中存在的题目：");
        System.out.println("期望输出：删除成功！");
        questionController.deleteQuestion(111111, response);

        System.out.println("删除题库中不存在的题目：");
        System.out.println("期望输出：输出显示该操作对系统的数据库无影响");
        questionController.deleteQuestion(444444, response);

        System.out.println("测试结束");
    }
}