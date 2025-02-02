package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Answer;
import com.scu.exam.service.AnswerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AnswerControllerTest {
    @Autowired
    private AnswerController answerController;
    @Autowired
    private AnswerService answerService;

    @Before
    public void setUp() throws Exception {
        Answer answer1 = new Answer(111111, "aaaaaaaaaaa");
        Answer answer2 = new Answer(111111, "bbbbbbbbbbb");
        Answer answer3 = new Answer(111111, "ccccccccccc");
        Answer answer4 = new Answer(111111, "ddddddddddd");
        answerService.insertAnswer(answer1);
        answerService.insertAnswer(answer2);
        answerService.insertAnswer(answer3);
        answerService.insertAnswer(answer4);
    }

    @After
    public void tearDown() throws Exception {
        List<Answer> answerList = answerService.findAllAnswer();
        System.out.println("运行后数据库中的数据：");
        for (int i = 0; i < answerList.size(); i++){
            Answer answer = answerList.get(i);
            System.out.println(answer.toString());
            answerService.deleteAnswer(answer);
        }
    }

    public JSONObject initJSONObject(Integer question_id){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("question_id",question_id);
        jsonObject.put("question_info", "sdfegve");
        jsonObject.put("correct_answer", "cvdfergf");
        jsonObject.put("type", "q");
        List<Answer> answerList = new List<Answer>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Answer> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Answer answer) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Answer> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Answer> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Answer get(int index) {
                return null;
            }

            @Override
            public Answer set(int index, Answer element) {
                return null;
            }

            @Override
            public void add(int index, Answer element) {

            }

            @Override
            public Answer remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Answer> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Answer> listIterator(int index) {
                return null;
            }

            @Override
            public List<Answer> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        Answer answer1 = new Answer(question_id, "vvvvvvvvvvv");
        Answer answer2 = new Answer(question_id, "lllllllllll");
        Answer answer3 = new Answer(question_id, "nnnnnnnnnnn");
        Answer answer4 = new Answer(question_id, "jjjjjjjjjjj");
        answerList.add(answer1);
        answerList.add(answer2);
        answerList.add(answer3);
        answerList.add(answer4);
        jsonObject.put("answerList", answerList);

        return jsonObject;
    }

    @Test
    public void addAnswer() {
        System.out.println("开始测试addAnswer():");

        System.out.println("插入符合规范的答案：");
        System.out.println("期望输出：插入成功后的数据库信息");
        JSONObject jsonObject = initJSONObject(222222);
        answerController.addAnswer(222222, jsonObject);

        System.out.println("测试结束");
    }

    @Test
    public void updateAnswer() {
        System.out.println("开始测试addAnswer():");

        System.out.println("符合规范的修改答案：");
        System.out.println("期望输出：修改成功后的数据库信息");
        JSONObject jsonObject = initJSONObject(111111);
        answerController.updateAnswer(jsonObject);

        System.out.println("测试结束");
    }

    @Test
    public void deleteAnswerById() {
        System.out.println("开始测试deleteAnswerById():");

        System.out.println("期望输出：删除成功后数据库中无数据了");
        answerController.deleteAnswerById(111111);

        System.out.println("测试结束");
    }

    @Test
    public void getAnswerById() {
        System.out.println("开始测试getAnswerById():");
        System.out.println("期望输出：查找到后的answerList信息");

        //List<Answer> answerList = answerController.getAnswerById(111111);
        //System.out.println("查找到的answerList如下：");
        //for (int i = 0; i < answerList.size(); i++){
        //    System.out.println("answer"+i+" :"+answerList.get(i).toString());
        //}

        //System.out.println("测试结束");
    }
}