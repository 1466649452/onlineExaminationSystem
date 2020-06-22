package com.scu.exam.service;

import com.scu.exam.pojo.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class QuestionServiceTest {
    @Autowired
    QuestionService questionService;

    @Before
    public void setUp() throws Exception {
        cleanQuestionTable();
        initQuestionTable();
        printAllQuestion();
    }


    @Test
    public void insertQuestion() {
        System.out.println("开始测试insertQuestion()：");
        printAllQuestion();
        System.out.println("测试结束");
    }

    @Test
    public void deleteQuestion() {
        System.out.println("开始测试deleteQuestion()：");
        Question question = questionService.findQuestionById(111111);
        questionService.deleteQuestion(question);
        printAllQuestion();
        System.out.println("测试结束");
    }

    @Test
    public void deleteQuestionById() {
        System.out.println("开始测试deleteQuestionById()：");
        questionService.deleteQuestionById(222222);
        printAllQuestion();
        System.out.println("测试结束");
    }

    @Test
    public void updateQuestion() {
        System.out.println("开始测试updateQuestion()：");
        Question question = new Question(333333, "rrrrrrrr", "dfwefe", "A");
        questionService.updateQuestion(question);
        printAllQuestion();
        System.out.println("测试结束");
    }

    @Test
    public void findQuestionById() {
        System.out.println("开始测试findQuestionById()：");
        Question question = questionService.findQuestionById(111111);
        System.out.println(question.toString());
        System.out.println("测试结束");
    }

    @Test
    public void findAllQuestion() {
        System.out.println("开始测试findAllQuestion()：");
        List<Question> questionList = questionService.findAllQuestion();
        for (int i = 0; i < questionList.size(); i++){
            System.out.println(questionList.get(i).toString());
        }
        System.out.println("测试结束");
    }

    public void initQuestionTable(){
        Question question1 = new Question(111111, "aaaaaaaa", "ssssssss", "q");
        Question question2 = new Question(222222, "zzzzzzzz", "xxxxxxxx", "a");
        Question question3 = new Question(333333, "qqqqqqqq", "wwwwwwww", "q");
        questionService.insertQuestion(question1);
        questionService.insertQuestion(question2);
        questionService.insertQuestion(question3);
    }

    public void cleanQuestionTable(){
        List<Question> questionList = questionService.findAllQuestion();
        for (int i = 0; i < questionList.size(); i++){
            questionService.deleteQuestion(questionList.get(i));
        }
    }

    public void printAllQuestion(){
        System.out.println("数据库中的怕有数据：");
        List<Question> questionList = questionService.findAllQuestion();
        for (int i = 0; i < questionList.size(); i++){
            System.out.println(questionList.get(i).toString());
        }
    }
}
