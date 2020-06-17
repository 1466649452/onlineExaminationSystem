package com.scu.exam.service;

import com.scu.exam.pojo.Question;
import com.scu.exam.service.QuestionService;
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

    @Test
    public void insertQuestion() {
        cleanQuestionTable();
        initQuestionTable();
        printAllQuestion();
    }

    @Test
    public void deleteQuestion() {
        cleanQuestionTable();
        initQuestionTable();
        Question question = questionService.findQuestionById(111111);
        questionService.deleteQuestion(question);
        printAllQuestion();
    }

    @Test
    public void deleteQuestionById() {
        cleanQuestionTable();
        initQuestionTable();
        questionService.deleteQuestionById(222222);
        printAllQuestion();
    }

    @Test
    public void updateQuestion() {
        cleanQuestionTable();
        initQuestionTable();
        Question question = new Question(333333, "rrrrrrrr", "dfwefe", "A");
        questionService.updateQuestion(question);
        printAllQuestion();
    }

    @Test
    public void findQuestionById() {
        cleanQuestionTable();
        initQuestionTable();
        Question question = questionService.findQuestionById(111111);
        System.out.println(question.toString());
    }

    @Test
    public void findAllQuestion() {
        cleanQuestionTable();
        initQuestionTable();
        List<Question> questionList = questionService.findAllQuestion();
        for (int i = 0; i < questionList.size(); i++){
            System.out.println(questionList.get(i).toString());
        }
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
        List<Question> questionList = questionService.findAllQuestion();
        for (int i = 0; i < questionList.size(); i++){
            System.out.println(questionList.get(i).toString());
        }
    }
}
