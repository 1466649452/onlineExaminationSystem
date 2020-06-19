package com.scu.exam.service;

import com.scu.exam.pojo.Answer;
import com.scu.exam.service.AnswerService;
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
public class AnswerServiceTest {
    @Autowired
    private AnswerService answerService;


    @Test
    public void insertAnswer() {
        cleanAnswerTable();
        initAnswerTable();
        printAllAnswer();
    }

//    @Test
//    public void deleteAnswer() {
//        cleanAnswerTable();
//        initAnswerTable();
//        Answer answer = answerService.findAnswerById(111111);
//        answerService.deleteAnswer(answer);
//        printAllAnswer();
//    }

    @Test
    public void deleteAnswerById() {
        cleanAnswerTable();
        initAnswerTable();
        answerService.deleteAnswerById(222222);
        printAllAnswer();
    }

    @Test
    public void updateAnswer() {
        cleanAnswerTable();
        initAnswerTable();
        Answer answer = new Answer(333333, "eeeeeeeeee");
        answerService.updateAnswer(answer);
        printAllAnswer();
    }

    @Test
    public void findAnswerById() {
        cleanAnswerTable();
        initAnswerTable();
        System.out.println(answerService.findAnswerById(222222));
        printAllAnswer();
    }

    public void initAnswerTable(){
        Answer answer1 = new Answer(111111, "aaaaaaaaaaa");
        Answer answer2 = new Answer(222222, "bbbbbbbbbbb");
        Answer answer3 = new Answer(333333, "ccccccccccc");
        answerService.insertAnswer(answer1);
        answerService.insertAnswer(answer2);
        answerService.insertAnswer(answer3);
    }

    public void cleanAnswerTable(){
        List<Answer> answerList = answerService.findAllAnswer();
        for (int i = 0; i < answerList.size(); i++){
            answerService.deleteAnswer(answerList.get(i));
        }
    }

    public void printAllAnswer(){
        List<Answer> answerList = answerService.findAllAnswer();
        for (int i = 0; i < answerList.size(); i++){
            System.out.println(answerList.get(i).toString());
        }
    }
}
