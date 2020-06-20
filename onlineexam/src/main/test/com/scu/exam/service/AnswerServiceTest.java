package com.scu.exam.service;

import com.scu.exam.pojo.Answer;
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
public class AnswerServiceTest {
    @Autowired
    private AnswerService answerService;


    @Before
    public void setUp() throws Exception {
        cleanAnswerTable();
        initAnswerTable();
    }

    @Test
    public void insertAnswer() {
        System.out.println("开始测试insertAnswer()：");
        printAllAnswer();
        System.out.println("测试结束");
    }

    @Test
    public void deleteAnswer() {
        System.out.println("开始测试deleteAnswer()：");
        List<Answer> answerList = answerService.findAnswerById(111111);
        for (int i = 0; i < answerList.size(); i++){
            answerService.deleteAnswer(answerList.get(i));
        }
        System.out.println("删除后的数据库：");
        printAllAnswer();
        System.out.println("测试结束");
    }

    @Test
    public void deleteAnswerById() {
        System.out.println("开始测试deleteAnswerById()：");
        answerService.deleteAnswerById(222222);
        printAllAnswer();
        System.out.println("测试结束");
    }

    @Test
    public void updateAnswer() {
        System.out.println("开始测试updateAnswer()：");
        Answer answer = new Answer(333333, "eeeeeeeeee");
        answerService.updateAnswer(answer);
        printAllAnswer();
        System.out.println("测试结束");
    }

    @Test
    public void findAnswerById() {
        System.out.println("开始测试findAnswerById()：");
        System.out.println(answerService.findAnswerById(222222));
        printAllAnswer();
        System.out.println("测试结束");
    }

    @Test
    public void findAllAnswer(){
        System.out.println("开始测试findAllAnswer()：");
        List<Answer> answerList = answerService.findAllAnswer();
        System.out.println("查找到的所有Administrator: ");
        for (int i = 0; i < answerList.size(); i++){
            System.out.println(answerList.get(i).toString());
        }
        System.out.println("测试结束");
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
        System.out.println("数据库中的数据：");
        List<Answer> answerList = answerService.findAllAnswer();
        for (int i = 0; i < answerList.size(); i++){
            System.out.println(answerList.get(i).toString());
        }
    }
}
