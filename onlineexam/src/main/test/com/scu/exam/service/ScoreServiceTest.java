package com.scu.exam.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.scu.exam.pojo.Score;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ScoreServiceTest {

    @Autowired
    private ScoreService scoreService;


    //增加数据
    //插入一条数据
    @Test
    public void insertOneTest() throws JsonProcessingException {
        JSONObject testap=new JSONObject();
        testap.put("b",1);
        Score tscore=new Score("1",1,98.5,new Date(),testap);
        scoreService.insertOneScore(tscore);
    }
    //插入多条数据
    @Test
    public void insertManyTest() throws JsonProcessingException {
        JSONObject testap=new JSONObject();
        testap.put("b",1);
        System.out.println(testap.get("b"));
        Score tscore1=new Score("1",1,98.5,new Date(),testap);
        Score tscore2=new Score("1",2,98.5,new Date(),testap);
        List<Score> scores=new ArrayList<Score>();
        scores.add(tscore1);
        scores.add(tscore2);
        scoreService.insetManyScore(scores);
    }

    //查找数据
    //根据id查询
    @Test
    public void using() throws IOException {
        List<Score> a=scoreService.findScoreBystuid("1");
        Iterator<Score> temp=a.iterator();
        while (temp.hasNext()){
            System.out.println(temp.next());
        }
    }

    //根据stu_id 和paper_id 唯一确定一条
    @Test
    public void findOneTest() throws IOException {
        Score a=scoreService.findOneScore("1",1);
        System.out.println(a.getScore());
    }

    //查询考试多少分以上的人
    @Test
    public void scoremorethan(){
        //List<Score> a=scoreService.findScoreCompare(1,70.0,"more");

        List<Score> b=scoreService.findScoreCompare(1,20,"more");
        List<Score> c = scoreService.findScoreCompare(1, 100, "less");
        Iterator<Score> temp1=b.iterator();
        while (temp1.hasNext()){
            System.out.println(c.contains(temp1.next()));
        }
        Iterator<Score> temp2=b.iterator();
        while (temp2.hasNext()){
            System.out.println(temp2.next());
        }
    }

    /*
    * 删除测试--通过
    * */
    @Test
    public void deleteTest(){
        Score tscore=new Score("1",2,98.5,new Date(),null);
        scoreService.insertOneScore(tscore);

        //System.out.println(scoreService.deleteOneScore("12345",1));
        //System.out.println(scoreService.deleteBystuid("12345"));
        System.out.println(scoreService.deleteOneScore("1",2));


    }


}
